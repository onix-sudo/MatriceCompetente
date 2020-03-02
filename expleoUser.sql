CREATE USER 'webcm_user_expleo'@'localhost' IDENTIFIED BY 'webcm_user_expleo';

GRANT ALL PRIVILEGES ON * . * TO 'webcm_user_expleo'@'localhost';

#
# Starting with MySQL 8.0.4, the MySQL team changed the 
# default authentication plugin for MySQL server 
# from mysql_native_password to caching_sha2_password.
#
# The command below will make the appropriate updates for your user account.
#
# See the MySQL Reference Manual for details: 
# https://dev.mysql.com/doc/refman/8.0/en/caching-sha2-pluggable-authentication.html
#
ALTER USER 'webcm_user_expleo'@'localhost' IDENTIFIED WITH mysql_native_password BY '2P@CJ*9YG`SS!ypg';