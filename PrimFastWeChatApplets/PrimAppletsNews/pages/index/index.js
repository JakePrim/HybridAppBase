//index.js
const app = getApp();
const typeMap = ['gn','gj','cj','yl','js','ty','other']; 
Page({
  data: {
    tabs: ['国内', '国际', '财经', '娱乐', '军事', '体育', '其他'],
    currentTab: 0,
    newsList: [],
    navbarData: {
      showCapsule: 0,
      title: "快读·资讯",
      chanBg: "#3299dc",
      titleColor: "#fff",
    },
    // 此页面 页面内容距最顶部的距离
    height: app.globalData.height * 2 + 20,
  },
  onLoad() {
    this.getNewsList();
  },
  getNewsList(callback){
    wx.request({
      url: 'https://test-miniprogram.com/api/news/list',
      data: {
        type: typeMap[this.data.currentTab],
      },
      success: res => { 
        let result = res.data.result;
        this.setData({
          newsList:result,
        });
        console.log("newsList:"+this.data.newsList.length);
       },
      complete: () => {
        callback && callback()
       }
    });
  },
  //点击tab
  changTab: function(e) {
    let index = e.target.dataset.current;
    if (this.data.current === index) {
      return false;
    } else {
      this.setData({
        currentTab: index
      });
    }
  },
  //滑动切换swiper
  swiperTab: function(e) {
    this.setData({
      currentTab: e.detail.current,
    });
    this.getNewsList();
  },
  //点击item
  clickItem:function(e){
    let data = e.currentTarget.dataset.bean;
    console.log("data:"+data.id);
    wx.navigateTo({
      url: '/pages/detail/detail?id=' + data.id,
    })
  },
  /**
  * 页面相关事件处理函数--监听用户下拉动作
  */
  onPullDownRefresh: function () {
    this.getNewsList(() => {
      wx.stopPullDownRefresh();
    });
  },
})