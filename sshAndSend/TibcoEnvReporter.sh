wget ftp://toolsadm/tibco/scripts/audit/TibcoEnvReporter-0.0.1.jar    
chmod +x TibcoEnvReporter-0.0.1.jar    
java -jar TibcoEnvReporter-0.0.1.jar    
echo y | mv report.txt /tmp/report.txt     
chmod 777  /tmp/report.txt    
rm -f TibcoEnvReporter-0.0.1.jar    
