import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from '@/views/layout/Index.vue'
import teacher_layout from '@/views/teacher/teacher.vue'
import Login from '@/views/login/Index.vue'
import Home from '@/views/home/Home.vue'
import teacher_home from '@/views/home/teacher_home.vue'
import Register from '@/views/register/index.vue'
import student_layout from '@/views/student/student.vue'
import student_home from '@/views/home/student_home.vue'
import student_courselist from '@/views/student/courselist.vue'
import student_courseSelection from '@/views/student/course_selection.vue'
const Teacher_list = () => import('@/views/user/list/Index.vue')
const Student_list = () => import('@/views/student_list/list/Index.vue')
const Course_list = () => import('@/views/feedback/list/Index.vue')
const class_list = () => import('@/views/feedback/list/classlist.vue')
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'login',
    component: Login,
  },
  {
    path: '/register',
    name: 'register',
    component: Register,
  },
  {
    path: '/login',
    name: 'login1',
    component: Login,
  },
  {
    path: '/admin',
    component: Layout,
    children: [
      {
        path: '',
        name: 'home',
        component: Home
      },
      {
        path: 'teacherlist',  //教师列表
        name: 'teacherlist',
        component: Teacher_list
      },
      {
        path: 'studentlist',  //学生列表
        name: 'studentlist',
        component: Student_list
      },
      {
        path: 'courselist',  //课程列表
        name: 'courselist',
        component: Course_list
      },
      {
        path: 'classlist',  //班级列表
        name: 'classlist',
        component: class_list
      },
    ]
  },
  {
    path: '/teacher',
    component: teacher_layout,
    children: [
      {
        path: '/',
        component: teacher_home,
      },
    ]
  },
  {
    path: '/student',
    component: student_layout,
    children: [
      {
        path: '/',
        component: student_home,
      },
      {
        path: 'courselist',
        component: student_courselist,
      },
      {
        path: 'course-selection',
        component: student_courseSelection,
      }
    ]
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router