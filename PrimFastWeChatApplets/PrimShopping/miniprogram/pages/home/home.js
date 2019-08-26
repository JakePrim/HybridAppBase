// pages/home/home.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    productList: [{
      id: 1,
      image: 'https://s3.cn-north-1.amazonaws.com.cn/u-img/product1.jpg',
      name: 'Wallet',
      price: 100,
      source: 'CHINA',
    }, {
      id: 2,
      image: 'https://s3.cn-north-1.amazonaws.com.cn/u-img/product2.jpg',
      name: 'Guitar',
      price: 200,
      source: 'SWEDEN',
    }, {
      id: 3,
      image: 'https://s3.cn-north-1.amazonaws.com.cn/u-img/product3.jpg',
      name: 'Stapler',
      price: 300,
      source: 'GERMANY',
    }, {
      id: 4,
      image: 'https://s3.cn-north-1.amazonaws.com.cn/u-img/product4.jpg',
      name: 'Leafy vegetables',
      price: 400,
      source: 'NEW ZEALAND',
    }, {
      id: 5,
      image: 'https://s3.cn-north-1.amazonaws.com.cn/u-img/product5.jpg',
      name: 'Compass',
      price: 500,
      source: 'USA',
    }], // Products List
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

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