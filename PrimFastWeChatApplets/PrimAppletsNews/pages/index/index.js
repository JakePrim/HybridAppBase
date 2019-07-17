//index.js
Page({
  data: {
    tabs: ['国内','国际','财经','娱乐','军事','体育','其他'],
    currentTab:0
  },
  onLoad(){
  },
  swiperTab:function(e){
    this.setData({
      currentTab:e.detail.current,
    });
  }
})
