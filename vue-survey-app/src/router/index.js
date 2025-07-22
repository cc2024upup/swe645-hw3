// src/router/index.js
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
