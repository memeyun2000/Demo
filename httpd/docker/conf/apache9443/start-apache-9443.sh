docker run -itd --name apache9443 \
	-p 9443:9443 -p 8080:80 \
	-v "/apps/apache2.4/docker/conf/apache9443/httpd.conf":"/usr/local/apache2/conf/httpd.conf" \
	-v "/apps/flink/flink-1.11.2/examples":"/usr/local/apache2/htdocs" \
	-v "/apps/apache2.4/docker/conf/apache9443/extra/httpd-ssl.conf":"/usr/local/apache2/conf/extra/httpd-ssl.conf" \
	-v "/apps/apache2.4/docker/conf/apache9443/ssl":"/usr/local/apache2/conf/ssl" \
	httpd
