
package kim.sihwan.daangnclone.config;

import kim.sihwan.daangnclone.domain.Area;
import kim.sihwan.daangnclone.domain.Member;
import kim.sihwan.daangnclone.domain.SelectedArea;
import kim.sihwan.daangnclone.repository.AreaRepository;
import kim.sihwan.daangnclone.repository.MemberRepository;
import kim.sihwan.daangnclone.repository.SelectedAreaRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
class AppRunnerConfig implements ApplicationRunner {

    private final AreaRepository areaRepository;
    private final RedisTemplate<String, List<String>> redisTemplate;
    private final MemberRepository memberRepository;
    private final SelectedAreaRepository selectedAreaRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {


        try{
            JSONParser jsonParser = new JSONParser();
            InputStream in5 = this.getClass().getResourceAsStream("/places.json");
            System.out.println(in5);

            Object obj = jsonParser.parse(new InputStreamReader(in5));
            JSONObject jsonObj = (JSONObject) jsonParser.parse(obj.toString());

            JSONArray array= (JSONArray) jsonObj.get("features");

            for(int i=0; i<array.size(); i++){
                JSONObject jj = (JSONObject)array.get(i);
                JSONObject jj2 = (JSONObject)jj.get("properties");
                String city =jj2.get("sido").toString();
                String gu =jj2.get("gu").toString();
                String dong =jj2.get("dong").toString();
                String fullAddress=city+" "+gu+" "+dong;
                String lat = jj2.get("lat").toString();
                String lng = jj2.get("lng").toString();

                Area area= Area.builder()
                        .address(fullAddress)
                        .city(city)
                        .gu(gu)
                        .dong(dong)
                        .lat(lat)
                        .lng(lng)
                        .build();

                areaRepository.save(area);

            }
        }catch (Exception e) {
        e.printStackTrace();

        }

        Member member = Member.builder()
                .username("test")
                .nickname("testNn")
                .password("testPw")
                .role("ROLE_USER")
                .area("만수3동")
                .build();

        memberRepository.save(member);


        List<Area> list = areaRepository.findAllByCityLike("%인천%");
        System.out.println(list.size());

        Area area = areaRepository.findById(835L).get();
        SelectedArea selectedArea = new SelectedArea();
        selectedArea.addMember(member);
        selectedArea.addArea(area);
        selectedAreaRepository.save(selectedArea);

        List<String> nearArea = new ArrayList<>();
        list.forEach(l->{
            double distanceMeter =
                    distance(Double.parseDouble(area.getLat()), Double.parseDouble(area.getLng()), Double.parseDouble(l.getLat()), Double.parseDouble(l.getLng()), "meter");
            if(distanceMeter<=3000) {
                nearArea.add(l.getDong());
                System.out.println(area.getDong() + "부터 " + l.getDong() + "까지의 거리는 : " + distanceMeter);
            }
        });
        List<String> gg = new ArrayList<>();
        gg.add("hi");
        gg.add("Hello");
        ValueOperations<String, List<String>> vo = redisTemplate.opsForValue();
        vo.set("a",gg);
        vo.set(area.getDong()+"::List",nearArea);
        System.out.println(area.getAddress());
        System.out.println("레디스! ");
        System.out.println(vo.get(area.getDong()+"::List"));







    }


    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        if (unit == "kilometer") {
            dist = dist * 1.609344;
        } else if(unit == "meter"){
            dist = dist * 1609.344;
        }

        return (dist);
    }


    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }



}

