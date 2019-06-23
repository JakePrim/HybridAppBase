// pages/list/list.js
const dayMap = ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'];
Page({


  /**
   * 页面的初始数据
   */
  data: {
    future:[]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getFuture()
  },

  getFuture(callback){
    wx.request({
      url: 'https://test-miniprogram.com/api/weather/future',
      data:{
         city:'北京市',
         time:new Date().getTime()
      },
      success: (res) => {
        let result = res.data.result;
        let r_future = [];
        for(let i=0;i<7;i++){
          let date = new Date();
          date.setDate(date.getDate()+i);
          r_future.push({
            weathericon: "/images/" + result[i].weather+"-icon.png",
            temp: result[i].minTemp + "°" + " - " + result[i].maxTemp +"°",
            wrok: dayMap[date.getDay()],
            date: `${date.getFullYear()}-${date.getMonth() - 1}-${date.getDate()}`
          })
        }
        r_future[0].wrok = "今天";
        this.setData({
          future: r_future
        })
      },
      complete: () => {
         callback && callback()
      },

    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
       this.getFuture(wx.stopPullDownRefresh())
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})