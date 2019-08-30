<?php 
header('Content-Type:application/json');
// 获取前端传递的注册信息 字段为   username   userpwd
$isUsername = array_key_exists('username',$_POST); 
$isUserpwd = array_key_exists('userpwd',$_POST); 
$username = $isUsername ? $_POST['username'] : '';
$userpwd = $isUserpwd ? $_POST['userpwd'] : '';
function printMsg($msg,$code){
    return array('msg'=>$msg,'code'=>$code);
}

if(!$username || !$userpwd){
    $msg = printMsg('参数有误',0);
    echo json_encode($msg);
    exit();
}

// 记录存储用户的文件路径
$fileStr = __DIR__.'/user.json';

// 读取现存的用户名和密码

$fileStream = fopen($fileStr,'r');

$fileContent = fread($fileStream,filesize($fileStr));
$fileContent_array = $fileContent ? json_decode($fileContent,true) : array();
fclose($fileStream);
// 判断用户名是否有重复的

$isrepeat = false;

foreach($fileContent_array as $key=>$val){
    if($val['username'] === $username){
        $isrepeat = true;
        break;
    }
}

if($isrepeat){
    $msg = printMsg('用户名重复',0);
    echo json_encode($msg);
    exit();
}

array_push($fileContent_array,array('username'=>$username,'userpwd'=>$userpwd));

// 将存储的用户名密码写入
$fileStream = fopen($fileStr,'w');
fwrite($fileStream,json_encode($fileContent_array));
fclose($fileStream);
echo json_encode(printMsg('注册成功',1));










?>