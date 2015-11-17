<?php
$username="rebekahl";$password="lj912953488";$database="student_rebekahl";
mysql_connect("sfsuswe.com", $username,$password)or die("can't connect to database server");
mysql_select_db($database)or die("can't connect to database");
//echo "connect to the server successfully!";

//echo "the name of the file is ".$_FILES['file']['name'];
//get the filename of image
$filename = isset($_FILES['file']['name']) ? $_FILES['file']['name'] : '';
//echo "the temporial name of image on the server is ".$_FILES['file']['tmp_name'];
$tmp_name = isset($_FILES['file']['tmp_name']) ? $_FILES['file']['tmp_name'] : '';
$imagename = isset($_POST['name']) ? $_POST['name'] : '';
//echo $imagename;
$description = isset($_POST['description']) ? $_POST['description'] : '';
//echo  $description;
//echo "description: ".$description;

  if(!empty($filename)){
        //make a loaction for storing images
        $location = "uploads/";
        $img1_dest = $location.$filename;
        $img2_dest = $location."medium".$filename;
        $img3_dest = $location."large".$filename;
        //create 2 physical thumbnails and direct them into the file system
        $source_image = imagecreatefromjpeg($tmp_name);
        $desired_width1 = imagesx($source_image) * 1.5;
        $desired_width2 = imagesy($source_image) * 2;
        make_thumb($tmp_name, $img2_dest, $desired_width1);
        make_thumb($tmp_name, $img3_dest, $desired_width2);
        //finish creating thumbnails. They're supposed to be in their physical location
        
        //move the uploaded iamge into the file system
        $movefile = move_uploaded_file($tmp_name, $img1_dest);
        if($movefile){
           //now need to insert data into database
           $hsl = mysql_query("insert into image values('','$imagename','$description')");
           if($hsl){
               echo "<script>alert('Insert to the database success!')</script>";
           }else{
               echo "<script>alert('Error Table database')</script>";
           }
           //display images
           display_image($img1_dest, $img2_dest, $img3_dest, $imagename, $description);
        }else{
           echo "upload directory is not writable";
        }
    }else{
        echo "Please choose a file to submit";
    }
function make_thumb($src, $dest, $desired_width) {

	/* read the source image */
	$source_image = imagecreatefromjpeg($src);
	$width = imagesx($source_image);
	$height = imagesy($source_image);
	
	/* find the "desired height" of this thumbnail, relative to the desired width  */
	$desired_height = floor($height * ($desired_width / $width));
	
	/* create a new, "virtual" image */
	$virtual_image = imagecreatetruecolor($desired_width, $desired_height);
	
	/* copy source image at a resized size */
	imagecopyresampled($virtual_image, $source_image, 0, 0, 0, 0, $desired_width, $desired_height, $width, $height);
        
	/* create the physical thumbnail image to its destination */
	imagejpeg($virtual_image, $dest);
}

function display_image($img1_dest, $img2_dest, $img3_dest, $imagename, $description){
    echo "<p>Name: $imagename</p>";
    echo "<p>Description: $description</p>";
    //echo $img1_dest;
    list($width, $height) = getimagesize($img1_dest);
    echo "<p>width: $width height: $height</p>";
    echo "<p><img src='$img1_dest' title='img1'/></p>";
    
    //echo $img2_dest;
    list($width, $height) = getimagesize($img2_dest);
    echo "<p>width: $width height: $height</p>";
    echo "<p><img src='$img2_dest' title='img2'/></p>";
    
    //echo $img3_dest;
    list($width, $height) = getimagesize($img3_dest);
    echo "<p>width: $width height: $height</p>";
    echo "<p><img src='$img3_dest' title='img3'/></p>";
}
mysql_close();
?>
