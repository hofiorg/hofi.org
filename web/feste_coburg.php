<?php 
$dbname="usr_web819_1";
$dbhost="localhost";
$dbuser="DBUSER";
$dbpass="DBPASSWORD";
$con = mysql_connect($dbhost,$dbuser,$dbpass);
mysql_select_db($dbname, $con);
if($_GET["insert"] == 'true') 
{ 
  $mail = $_GET['mail'];
  if($mail == 'deine@email' || $mail == '')
  {
    echo '<script type="text/javascript">document.location.href=\'http://www.3giganten.de/newsletter.html\'</script>';
    exit();
  }
  echo '<script type="text/javascript">document.location.href=\'http://www.3giganten.de\'</script>';
  mysql_query("INSERT INTO partymails (mail) VALUES ('$mail')");
} 
else 
{ 
  if($_GET["password"] != 'leue') 
    exit(); 
  $result = mysql_query("select * from partymails");
  while($row = mysql_fetch_array($result))
  {
    echo $row[0];
    echo "<br />";
  }
} 
mysql_close($con);
?>
