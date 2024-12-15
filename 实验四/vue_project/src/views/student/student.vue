<template>
    <div class="layout">
        <!-- 左侧导航 -->
        <div class="menu">
            <Menu :isCollapse="isCollapse"></Menu>
        </div>
        <!-- 右侧导航 -->
        <div class="content" :class="{ small: isCollapse }">
            <Content @changeShow="changeShow" :isCollapse="isCollapse"></Content>
        </div>
    </div>


</template>
<script>
import Content from '@/views/layout/content/student_content.vue'
import Menu from '@/views/layout/menu/student_menu.vue'
export default {
    components: {
        Menu,
        Content
    },
    data() {
        return {
            isCollapse: false
        }
    },
    methods: {
        changeShow() {
            this.isCollapse = !this.isCollapse;
        }
    }

}
</script>
<style lang="less">
.layout {
    .menu {
        // width: 200px;
        background: #112f50;
        position: fixed;
        left: 0;
        top: 0;
        bottom: 0;
    }

    .content {
        padding-left: 200px;
    }

    .small {
        padding-left: 64px;
    }
}
</style>