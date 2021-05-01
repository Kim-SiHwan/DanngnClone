<template>
  <v-app>
    <v-container>
      <v-row align="center" class="fill-height; mt-15" justify="center">
        <div class=" elevation-15" style="width: 600px; height: 600px" >
          <h3 class="text-center pt-15 py-3 black--text">
            로그인
          </h3>

          <v-form class="pa-15 text-center" ref="form">

            <v-text-field class=" pl-3 pr-3" label="Name" prepend-icon="mdi-account"
                          required type="text" v-model="username">
            </v-text-field>

            <v-text-field  class="pt-10 pl-3 pr-3" label="Password" prepend-icon="mdi-lock"
                           required type="password" v-model="password"
                           v-on:keyup.enter="login">
            </v-text-field>

            <v-btn
                depressed
                color="primary"
                @click="login"
                large>
              로그인
            </v-btn>
          </v-form>
        </div>
      </v-row>
    </v-container>
  </v-app>
</template>

<script>

export default {
  name: "Login",
  data(){
    return{
      username:'',
      password:'',
      loginDto:''
    }
  },
  methods: {
    login() {
      this.loginDto = {
        username: this.username,
        password: this.password
      }
      this.$store.dispatch('REQUEST_LOGIN',this.loginDto);

    },
    logout() {
      this.$store.dispatch('REQUEST_LOGOUT');
      this.$store.commit('setSnackBar', {msg: '로그아웃 완료', color: 'success'});
    }
  }
}
</script>

<style scoped>

</style>