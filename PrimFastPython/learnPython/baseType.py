import time
#pds_width = 1000
#su = 8
#print(pds_width/su)
# 类型转换
#print(int('8'))
#print(str(10))
#print(bool(1),bool(0))
# 序列
chinese_zodia = "猴鸡狗猪鼠牛虎兔龙蛇马羊"
# 访问序列中的某一个 切片操作
#print(chinese_zodia[0])
# 访问序列中的几个
#print(chinese_zodia[0:4]) #输出前四个
# 倒序访问序列
#print(chinese_zodia[-1]) #输出倒数第一个
year = 2019
print(year % 12)
print(chinese_zodia[year % 12])

# 字符串的常用操作
# [not]in 序列
#print("狗" in chinese_zodia)
#print("狗" not in chinese_zodia)
# 序列 + 序列 连接操作
#print(chinese_zodia+"abc")
# 序列 * 整数 重复操作 表示字符串重复几次
#print(chinese_zodia * 3)

# 元组
# 定义元组
zodiac_name = (u'魔蝎座',u'水瓶座',u'双鱼座',u'白羊座',u'金牛座',u'双子座',u'巨蟹座',u'狮子座'
               ,u'处女座',u'天秤座',u'天蝎座',u'射手座');
# 元组嵌套
zodiac_days = ((1,20),(2,19),(3,21),(4,21),(5,21),(6,22),(7,23),(8,23),(9,23),(10,23),(11,23),(12,23))

# (1,20) 可以看作为120 (1,20) < (2,20)
(month,day) = (11,21)

# 比较 直到啊(month,day) 小于某个元素停止
zodiac_day = filter(lambda x : x <= (month,day),zodiac_days)

zodiac_len = len(list(zodiac_day)) #len是从1开始的注意越界的问题

print(zodiac_len);
# 注意这里要对zodiac_len取余 否则zodiac_day >= 12 而元组是从下标0开始的否则会数组越界的问题
print(zodiac_name[zodiac_len % 12])

#列表
a_list = ["ab","xys","cd","dd"]
# 列表中添加元素
a_list.append("X")
print(a_list)
# 列表删除某个元素 列表不支持移除下标吗？
a_list.remove("xys")
print(a_list)
# 列表的操作和字符串的操作类似
# 索引
print(a_list[1],a_list[-1])
# 切片
print(a_list[0:2],a_list[-3:])


# 拼接操作
a_list = a_list + [1,2,3,4]
print(a_list)

# 切片赋值
a_list[2:4]=['p','r']
print(a_list)
# 切片移除
a_list[2:4] = []
print(a_list)

# 切片清空整个列表
a_list[:] = []
print(a_list)

#列表嵌套
b_list = [['a','b','c'],['d','e','f']]
print(b_list[0],b_list[0][1])

#这里使用的filter()函数的返回类型叫迭代器，它是我们后面要讲的一种函数功能。
# filter函数返回的内容类似一根长长的管子，里面按顺序依次存好要输出的元素，
# 使用list()函数可以一次性将管子里的数据都取出来，第二次再去取管子中自然是空的了。

#例如
# num = [1,2,3,4,5]
# a = iter(num) #把列表转换成迭代器
# print(type(num))
# print(type(a))
#
# print(list(a)) #一次返回所有内容 管子中已经没了
# print(list(a))

# 同理filter也是一个迭代器
# b = filter(lambda x:x<=5,num)
# print(b.__next__()) #取第一个
# print(b.__next__()) #取第二个
# print(list(b))#全部取出来
# time.sleep(1)#停留1s观察结果
# b.__next__();# 异常StopIteration，告诉用户已经没有可取内容了