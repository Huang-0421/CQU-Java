<template>
  <div class="home">
    <div class="header">
      <div class="item">
        教师人数
        <div class="num">{{ hotDoctors }}</div>
      </div>
      <div class="item">
        学院数量
        <div class="num">{{ doctorsOnDuty }}</div>
      </div>
      <div class="item">
        学生数量
        <div class="num">{{ doctorsOnDutyRatio }}</div>
      </div>
      <div class="item">
        网页浏览量
        <div class="num">{{ todaysOutpatients }}</div>
      </div>
    </div>

     <!-- 2. 访问数据统计  -->
    <div class="content">
      <div class="time-info" id="box13" style="width: 580px; height: 340px">
        <div id="charts" style="width: 100%; height: 340px">
          <AgeChart></AgeChart>
        </div>
      </div>
      <div class="area" id="box1" style="width: 360px; height: 340px">
        <div id="charts" style="width: 100%; height: 340px">
          <ProductSalesEcharts></ProductSalesEcharts>
        </div>
      </div>
    </div>
    <div style="background-color: white; margin-top: 58px">
      <BookTimeLine></BookTimeLine>
    </div>
    <!-- <div class="content">
      <div class="time-info" style="width: 580px; height: 340px">
        <div style="width: 100%; height: 340px">
          <RoomCol></RoomCol>
        </div>
      </div>
      <div class="area" style="width: 360px; height: 340px">
        <div class="gender-pie-container">
          <GenderPie></GenderPie>
        </div>
      </div>
    </div> -->

    <div class="heatmap-container">
      <Heatmap></Heatmap>
    </div>
  </div>
</template>

<script>
import AgeChart from "./echarts/AgeChart.vue";
import ProductSalesEcharts from "./echarts/ProductSalesRatio.vue";
import Heatmap from "./echarts/test.vue";
import GenderPie from "./echarts/GenderPie.vue";
import RoomCol from "./echarts/RoomCol.vue";
import BookTimeLine from "./echarts/BookTimeLine.vue";

export default {
  data() {
    return {
      // 将 data 改为函数，返回一个对象
      hotDoctors: 0, // 热门医生数量
      doctorsOnDuty: 0, // 医生在岗人数
      doctorsOnDutyRatio: 0, // 医生在岗比例
      todaysOutpatients: 0, // 今日门诊数量
    };
  },
  mounted() {
    console.log("Component mounted"); // 用于调试生命周期钩子
    this.fetchData(); // 在组件挂载时获取数据
  },
  components: {
    AgeChart,
    ProductSalesEcharts,
    Heatmap,
    GenderPie,
    RoomCol,
    BookTimeLine,
  },
  methods: {
    async fetchData() {
      try {
        this.hotDoctors = 20; // 热门医生数量保持为0
        this.doctorsOnDuty = 6;
        this.doctorsOnDutyRatio = 130;
        this.todaysOutpatients = 37; // 今日门诊数量保持为0
      } catch (error) {
        console.error("获取数据失败:", error);
      }
    },
  },
};
</script>

<style lang="less" scoped>
.home {
  height: 760px !important;
}

.header {
  display: flex !important;
  padding-right: 30px !important;
}

.item {
  flex: 1 !important;
  height: 100px !important;
  padding: 10px !important;
  background: #fff !important;
  border-radius: 10px !important;
  margin-left: 20px !important;
  margin-right: 20px !important;
  font-weight: bold !important;
  color: #fff !important;
  position: relative !important;
}

.num {
  font-size: 22px !important;
  margin: 10px !important;
  color: #ffffff !important;
}

.bottom {
  position: absolute !important;
  border-top: 1px solid rgb(246, 245, 245) !important;
  padding: 10px 20px !important;
  bottom: 0 !important;
  right: 0 !important;
  left: 0 !important;
  color: #fff !important;
  font-weight: normal !important;
}

.item:nth-child(1) {
  background-image: linear-gradient(#df887d, #88554d) !important;
}

.item:nth-child(2) {
  background-image: linear-gradient(#409eff, #2e556e) !important;
}

.item:nth-child(3) {
  background-image: linear-gradient(#b54fa8, #713c7a) !important;
}

.item:nth-child(4) {
  background-image: linear-gradient(#1cd2f1, #39717a) !important;
}

.content {
  margin: 20px !important;
  padding-left: 0px !important;
  display: flex !important;
  height: 320px !important;
}

.time-info {
  flex: 1.6 !important;
  background: #fff !important;
  margin-right: 20px !important;
  padding: 10px !important;
}

.area {
  flex: 1 !important;
  background: #fff !important;
  padding: 10px !important;
}

.home-footer {
  display: flex !important;
  padding-left: 20px !important;
  margin-top: 70px !important;
}

.box-card {
  flex: 1 !important;
  margin-right: 30px !important;
}

span {
  font-weight: 600 !important;
}

</style>