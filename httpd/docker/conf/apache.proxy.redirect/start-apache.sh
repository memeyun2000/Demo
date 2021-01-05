docker run -itd --name redirect.proxy.apache \
	-p 8080:80 -p 8081:8081 -p 8082:8082 -p 8083:8083 \
	-v "/apps/apache2.4/docker/conf/apache.proxy.redirect/httpd.conf":"/usr/local/apache2/conf/httpd.conf" \
	-v "/apps/apache2.4/docker/conf/apache.proxy.redirect/redirect.html":"/usr/local/apache2/htdocs/redirect.html" \
	-v "/apps/flink/flink-1.11.2/examples":"/usr/local/apache2/htdocs" httpd
