<template>
  <div>
    <el-row :gutter="10" style="margin-bottom: 60px">
      <el-col :span="6">
        <el-card style="color: #409eff">
          <div><i class="el-icon-user-solid">用户总数</i></div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            100
          </div>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card style="color: #67c23a">
          <div><i class="el-icon-money">销售总量</i></div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            ￥100000000
          </div>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card style="color: #f56c6c">
          <div><i class="el-icon-bank-card">收益总额</i></div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            ￥300000
          </div>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card style="color: #e6a23c">
          <div><i class="el-icon-s-shop">门店总数</i></div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            100
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="12">
        <div id="main" style="width: 500px; height: 400px"></div>
      </el-col>

      <el-col :span="12">
        <div id="pie" style="width: 500px; height: 400px"></div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import * as echarts from "echarts";

export default {
  data() {
    return {};
  },
  mounted() {
    //页面元素渲染之后再触发

    var option = {
      title: {
        text: "季度会员数量统计",
        subtext: "趋势图",
        left: "center",
      },
      tooltip: {
        trigger: "item",
      },
      legend: {
        orient: "vertical",
        left: "left",
      },
      xAxis: {
        type: "category",
        data: ["第一季度", "第二季度", "第三季度", "第四季度"],
      },
      yAxis: {
        type: "value",
      },
      series: [
        {
          name: "星巴克",
          data: [],
          type: "line",
        },
        {
          name: "星巴克",
          data: [],
          type: "bar",
        },
        {
          name: "瑞星咖啡",
          data: [],
          type: "bar",
        },
        {
          name: "瑞星咖啡",
          data: [],
          type: "line",
        },
      ],
    };

    //饼图
    var picOption = {
      title: {
        text: "季度会员数量统计",
        subtext: "比例图",
        left: "center",
      },
      tooltip: {
        trigger: "item",
        formatter: '{a} <br/>{b} : {c} ({d}%)'
      },
      legend: {
        orient: "vertical",
        left: "left",
      },
      series: [
        {
          name: "星巴克",
          center: ['25%', '50%'],
          type: "pie",
          radius: "50%",
          label: {
            normal: {
              show: true,
              position: "inner", //标签的位置
              textStyle: {
                fontWeight: 300,
                fontSize: 16,
                color: "#fff",
              },
              formatter: "{d}%",
            },
          },
          data: [],
        },
        {
          name: "瑞星咖啡",
          type: "pie",
          center: ['75%', '50%'],
          radius: "50%",
          label: {
            normal: {
              show: true,
              position: "inner", //标签的位置
              textStyle: {
                fontWeight: 300,
                fontSize: 16,
                color: "#fff",
              },
              formatter: "{d}%",
            },
          },
          data: [
            { name: "第一季度", value: 10 },
            { name: "第二季度", value: 6 },
            { name: "第三季度", value: 7 },
            { name: "第四季度", value: 8 }
          ],
        },
      ],
    };

    //折线图
    var chartDom = document.getElementById("main");
    var myChart = echarts.init(chartDom);

    //饼图
    var pietDom = document.getElementById("pie");
    var pieChart = echarts.init(pietDom);

    //折线图后台数据
    this.request.get("/echarts/menber").then((res) => {
      // 填空
      // option.xAxis.data = res.data.x
      option.series[0].data = res.data;
      option.series[1].data = res.data; //星巴克

      option.series[2].data = [5, 6, 7, 8];
      option.series[3].data = [5, 6, 7, 8]; //瑞星咖啡
      // 数据准备完毕之后再set
      myChart.setOption(option);

      //饼图
      picOption.series[0].data = [
        { name: "第一季度", value: res.data[0] },
        { name: "第二季度", value: res.data[1] },
        { name: "第三季度", value: res.data[2] },
        { name: "第四季度", value: res.data[3] },
      ];
      pieChart.setOption(picOption);
    });
  },
};
</script>

<style></style>
