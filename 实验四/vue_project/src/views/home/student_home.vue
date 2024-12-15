<template>
    <div class="block">
        <el-carousel trigger="click" height="83vh">
            <el-carousel-item v-for="(item, index) in images" :key="index">
                <img :src="item" alt="carousel image" class="carousel-image" />
            </el-carousel-item>
        </el-carousel>
    </div>
</template>

<script>
export default {
    data() {
        return {
            // 自定义图片数组，注意图片路径
            images: [
                '/image/4.jpg',
                '/image/1.jpg', // 绝对路径
                '/image/2.jpg',
                '/image/3.jpg',
            ]
        };
    }
};
</script>

<style scoped>
.el-carousel__item {
    display: flex;
    justify-content: center;
    align-items: center;
}

.carousel-image {
    max-width: 100%;
    /* 最大宽度为容器宽度 */
    max-height: 100%;
    /* 最大高度为容器高度 */
    object-fit: cover;
    /* 保持原始比例，且完整显示图片 */
}


.el-carousel {
    width: 100%;
    height: 100vh;
    /* 设置走马灯高度为全屏 */
}

.el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
}
</style>