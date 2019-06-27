//index.js
//获取应用实例
const app = getApp()

const weatherMap = {
  'sunny': '晴天',
  'cloudy': '多云',
  'overcast': '阴',
  'lightrain': '小雨',
  'heavyrain': '大雨',
  'snow': '雪'
}

const weatherColorMap = {
  'sunny': '#cbeefd',
  'cloudy': '#deeef6',
  'overcast': '#c6ced2',
  'lightrain': '#bdd5e1',
  'heavyrain': '#c5ccd0',
  'snow': '#aae1fc'
}

//引入位置核心SDK
const QQMapWx = require('../../libs/qqmap-wx-jssdk.js');
var qqmapsdk;
Page({
  onLoad(){
    qqmapsdk = new QQMapWx({
      key:'SZGBZ-64HEI-RVTG7-5GPKJ-GGV46-LEBTG'
    });
    this.getNowData()
  },
  
  data:{
    now_temp:"",
    now_weather:"",
    now_weather_bg:"",
    forecast:[],
    toDayTempDate:""
  },
  getNowData(callback) {
    wx.request({
      url: 'https://test-miniprogram.com/api/weather/now',
      data: {
        'city': "北京"
      },
      success: res => {
        let result = res.data.result;
        console.log(result);
        this.setNow(result);
        this.setForecast(result);
        this.setToDayTemp(result);
      },
      complete: () => {
        callback && callback()
      }
    })
  },
  onPullDownRefresh(){
    this.getNowData(()=>{wx.stopPullDownRefresh()})
  },
  setNow(result) {
    let temp = result.now.temp;
    let weather = result.now.weather;

    wx.setNavigationBarColor({
      frontColor: '#000000',
      backgroundColor: weatherColorMap[weather],
    });

    this.setData({
      now_temp: temp + "°",
      now_weather: weatherMap[weather],
      now_weather_bg: "/images/" + weather + '-bg.png'
    })
  },
  setForecast(result){
    let r_forecast = [];
    let n_f = result.forecast;
    console.log(n_f);
    let nowHouse = new Date().getHours();
    for (let i = 0; i < 8; i++) {
      r_forecast.push({
        time: (i*3 + nowHouse) % 24 + "时",
        iconPath: "/images/" + n_f[i].weather + "-icon.png",
        temp: n_f[i].temp + "°"
      });
    }
    r_forecast[0].time = "现在";
    this.setData({
      forecast: r_forecast
    })
  },
  setToDayTemp(result){
     let date = new Date();
     this.setData({
       toDayTempDate: `${date.getFullYear()}-${date.getMonth() - 1}-${date.getDate()} 今天` + ` ${result.today.minTemp}°-${result.today.maxTemp}°`
     });
  },
  onTabDayWeather(){
    wx.navigateTo({
      url: '/pages/list/list',
    })
  },
  //获取当前的经纬度
  toTabLocation(){
      wx.getLocation({
        success: function(res) {
          console.log(res.latitude,res.longitude);
          qqmapsdk.reverseGeocoder({
              location:{
                latitude:res.latitude,
                longitude: res.longitude
              },
              success:res =>{
                let city = res.result.address.city;
                console.log("city:"+city);
              }
          });
        },
      });
      //SZGBZ-64HEI-RVTG7-5GPKJ-GGV46-LEBTG
      //https://apis.map.qq.com

  }
})
