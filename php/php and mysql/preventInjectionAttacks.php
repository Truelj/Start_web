<?php
/**
 * Created by PhpStorm.
 * User: jieli
 * Date: 9/8/16
 * Time: 12:13 AM
 */
// functions for preventing both SQL and XSS injection attacks
    function mysql_entities_fix_string($conn, $string){
        return htmlentities(mysql_fix_string($conn, $string));
    }
    function mysql_fix_string($conn, $string){
        if(get_magic_quotes_gpc()) $string = stripslashes($string);
        return $conn->real_escape_string($string);
    }