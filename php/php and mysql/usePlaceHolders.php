<?php
/**
 * Created by PhpStorm.
 * User: jieli
 * Date: 9/8/16
 * Time: 12:00 AM
 */
    require_once 'login.php';
    $conn = new mysqli($hn, $un, $pw, $db);
    if($conn->connect_error) die("die".$conn->connect_error);

    $statement = $conn->prepare("INSERT INTO classics VALUES (?,?,?,?,?)");
    $statement->bind_param('sssss', $author, $title, $category, $year, $isbn);

    $author = 'Emily Bronte';
    $title = 'Wuthering heights';
    $category = 'Classic Fiction';
    $year = '1847';
    $isbn = '9780553212587';
    $statement->execute();
    printf("%d Row inserted.\n", $statement->affected_rows);

    $statement->close();
    $conn->close();
?>