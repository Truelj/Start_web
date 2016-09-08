<?php
/**
 * Created by PhpStorm.
 * User: jieli
 * Date: 9/7/16
 * Time: 10:52 PM
 */
require_once 'login.php';
$conn = new mysqli($hn, $un, $pw, $db);
if($conn->connect_error) die($conn->connect_error);

$query = "SELECT * FROM classics";
$result = $conn->query($query);
if($result->error) die($conn->error);

$rows = $result->num_rows;
for($j=0;$j < $rows; $j++){
    $result->data_seek($j);//fetch one row
    echo 'Author: '.$result->fetch_assoc()['author'].'<br>';
    $result->data_seek($j);//fetch one row
    echo 'Title: '.$result->fetch_assoc()['title'].'<br>';
    $result->data_seek($j);//fetch one row
    echo 'Category: '.$result->fetch_assoc()['category'].'<br>';
    $result->data_seek($j);//fetch one row
    echo 'Year: '.$result->fetch_assoc()['year'].'<br>';
    $result->data_seek($j);//fetch one row
    echo 'ISBN: '.$result->fetch_assoc()['isbn'].'<br>';
    echo '<br>';
}
$result->close();
$conn->close();

?>