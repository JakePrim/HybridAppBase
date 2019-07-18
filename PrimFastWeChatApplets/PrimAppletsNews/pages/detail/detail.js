// pages/detail/detail.js
const app = getApp();
Page({
  /**
   * 页面的初始数据
   */
  data: {
    id: "",
    contents: [],
    title: "",
    date: "",
    source: "",
    firstImage: "",
    readCount:0,
    navbarData: {
      showCapsule: 1,
      title: "快读·资讯",
      chanBg: "#fff",
      titleColor: "#000",
    },
    height: app.globalData.height * 2 + 20,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    this.setData({
      id: options.id
    });
    this.getDetailData();
  },
  getDetailData(callback) {
    wx.request({
      url: 'https://test-miniprogram.com/api/news/detail',
      data: {
        id: this.data.id,
      },
      success: res => {
        let result = res.data.result;
        this.setData({
          contents: result['content'],
          title: result['title'],
          date: result['date'],
          source: result['source'],
          readCount: result['readCount'],
          firstImage: result['firstImage'],
        });
        console.log("title:" + this.data.title);
      },
      complete: () => {
        callback && callback();
      }
    })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {
     this.getDetailData(()=>{
       wx.stopPullDownRefresh();
     });
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  }
})