//index.js
//获取应用实例
const app = getApp()

const UNPROMPTED = 0;
const UNAUTHORIZED = 1;
const AUTHORIZED = 2;

const UNPROMPTED_TIPS = "点击获取当前位置";
const UNAUTHORIZED_TIPS = "点击开启位置权限";
const AUTHORIZED_TIPS = "";

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
Page({
  //页面开始加载
  onLoad(){
    this.qqmapsdk = new QQMapWx({
      key:'SZGBZ-64HEI-RVTG7-5GPKJ-GGV46-LEBTG'
    });
    // //请求定位之后 在获取天气信息
    // this.toTabLocation();
    this.getNowData();
  },
  //需要保存的数据
  data:{
    now_temp:"",
    now_weather:"",
    now_weather_bg:"",
    forecast:[],
    toDayTempDate:"",
    city:"石家庄市",
    locationTipsText: UNPROMPTED_TIPS,
    locationAuthType: UNPROMPTED
  },
  onShow(){
    console.log("onShow");
    wx.getSetting({
      success:res=>{
        console.log("success:" + res);
      },
      fail:res=>{
        console.log("fail:"+res);
      }
    });
  },
  onReady(){

  },
   
  //获取天气信息
  getNowData(callback) {
    wx.request({
      url: 'https://test-miniprogram.com/api/weather/now',
      data: {
        'city': this.data.city
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
  //下拉刷新
  onPullDownRefresh(){
    this.getNowData(()=>{wx.stopPullDownRefresh()})
  },
  //设置当前的天气信息
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
  //设置未来的天气信息
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
  //设置当天的天气温度范围
  setToDayTemp(result){
     let date = new Date();
     this.setData({
       toDayTempDate: `${date.getFullYear()}-${date.getMonth() - 1}-${date.getDate()} 今天` + ` ${result.today.minTemp}°-${result.today.maxTemp}°`
     });
  },
  //跳转到未来天气列表
  onTabDayWeather(){
    wx.navigateTo({
      url: '/pages/list/list?city='+this.data.city,
    })
  },
  //获取位置权限
  toTabLocation(){
    console.log("locationAuthType:" + this.data.locationAuthType);
     //判断权限是否授权
     if(this.data.locationAuthType === UNAUTHORIZED){
       //进行授权
       wx.openSetting();
     }else{
       this.getLocation();
     }
  },
  ////获取当前位置的经纬度,得到当前的城市
  getLocation(){
    wx.getLocation({
      success: res => {
        this.setData({
          locationTipsText: AUTHORIZED_TIPS,
          locationAuthType: AUTHORIZED
        });
        this.qqmapsdk.reverseGeocoder({
          location: {
            latitude: res.latitude,
            longitude: res.longitude
          },
          success: res => {
            let r_city = res.result.address_component.city;
            console.log("city:" + r_city);
            this.setData({
              city: r_city
            });
            this.getNowData()
          },
          fail: function (error) {
            console.error(error);
            this.setData({
              city: r_city,
              locationTipsText: UNAUTHORIZED_TIPS,
              locationAuthType: UNAUTHORIZED
            });
          }
        });
      },
    });
      //SZGBZ-64HEI-RVTG7-5GPKJ-GGV46-LEBTG
      //https://apis.map.qq.com
  }
})
