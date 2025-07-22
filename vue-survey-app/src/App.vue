import { createRouter, createWebHistory } from 'vue-router';
import SurveyForm from '@/components/SurveyForm.vue';
import SurveyList from '@/components/SurveyList.vue';

const routes = [
  { path: '/', name: 'SurveyForm', component: SurveyForm },
  { path: '/submissions', name: 'SurveyList', component: SurveyList },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;

<template>
  <div>
    <nav class="navbar navbar-expand navbar-light bg-light px-3">
      <router-link to="/" class="navbar-brand">Student Survey</router-link>
      <div class="navbar-nav">
        <router-link to="/" class="nav-link">Submit Survey</router-link>
        <router-link to="/submissions" class="nav-link">View Submissions</router-link>
      </div>
    </nav>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'App',
};
</script>

<style>
body { background-color: #f9f9f9; }
</style>


<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
