<?php //sqltest.php
/**
 * Created by PhpStorm.
 * User: jieli
 * Date: 9/7/16
 * Time: 11:21 PM
 */
    require_once 'login.php';
    //create a mysqli connection
    $conn = new mysqli($hn,$un,$pw,$db);
    if($conn->connect_error) die($conn->connect_error);

    //test the browser data
    if(isset($_POST['delete']) && isset($_POST['isbn'])){
        $isbn = get_post($conn, 'isbn');
        $query = "DELETE FROM classics WHERE isbn='$isbn'";
        $result = $conn->query($query);
        if(!$result) echo 'DELETE failed : $query<br>'.$conn->error."<br><br>";

    }
    if(isset($_POST['author'])
        && isset($_POST['title'])
        && isset($_POST['category'])
        && isset($_POST['year'])
        && isset($_POST['isbn']))
    {
        $author = get_post($conn, 'author');
        $title = get_post($conn, 'title');
        $category = get_post($conn, 'category');
        $year = get_post($conn,'year');
        $isbn = get_post($conn, 'isbn');
        $query = "INSERT INTO classics VALUE". "('$author','$title','$category','$year','$isbn')";
        $result = $conn->query($query);
        if(!$result) echo "INSERT failed: $query <br>".$conn->error."<br><br>";
    }

    echo<<<_END
    <form action="sqltest.php" method="POST"> <pre>
        Author   <input type="text" name="author">
        Title    <input type="text" name="title">
        Category <input type="text" name="category">
        Year     <input type="text" name="year">
        ISBN     <input type="text" name="isbn">
                 <input type="submit" value="ADD RECORD">
    </pre></form>
_END;
    //display from database
    $query = "SELECT * FROM classics";
    $result = $conn->query($query);
    if(!$result) die("Database access failed: ".$conn->error);

    $rows = $result->num_rows;
    for($j=0; $j<$rows;$j++){
        $result->data_seek($j);//fetch one row
        $row = $result->fetch_array(MYSQLI_NUM);
        //display data
        echo <<< END
        <pre>
            Author: $row[0];
            Title: $row[1];
            Category: $row[2];
            Year: $row[3];
            ISBN: $row[4];
        </pre>
        <form method="POST" method="sqltest.php">
            <input type="hidden" name="delete" value="yes">
            <input type="hidden" name="isbn" value="$row[4]">
            <input type="submit" value="DELETE RECORD">
        </form>
END;
    }//end of for
    $result->close();
    $conn->close();

    //function definition
    function get_post($conn, $var){
        return $conn->real_escape_string($_POST[$var]);
    }
?>
