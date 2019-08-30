<?php 
header('Content-Type:application/json');
$isUsername = array_key_exists('username',$_POST); 
$username = $isUsername ? $_POST['username'] : '';

if(!$username){
    $msg = printMsg('参数有误',0);
    echo json_encode($msg);
    exit();
}

function printMsg($msg,$code){
    return array('msg'=>$msg,'code'=>$code);
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
$msg = printMsg('用户名可用',1);
echo json_encode($msg);
?>
