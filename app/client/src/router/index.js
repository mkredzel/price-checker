import Vue from 'vue'
import VueRouter from 'vue-router'
import TV from '../components/TV.vue'

Vue.use(VueRouter)

  const routes = [
    
  {
    path: '/tvs/search',
    name: 'tvs',
    component: TV
  }
]

const router = new VueRouter({
  routes
})

export default router
