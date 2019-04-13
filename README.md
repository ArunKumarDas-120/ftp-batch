# ftp-batch

DB Configuration
---------------------------

1) make directory of any name <drive:\H2Database>
2) Copy h2 inside the directory.
3) create directory structure to store database files <drive:\H2Database\databases>
4) run command:- java -cp h2.jar org.h2.tools.Server -web -webPort 2000 -webAllowOthers -tcp -tcpAllowOthers -tcpPort 3000
   this will start h2 database in server mode
   
   
FTP Conficuration
----------------------------
1. Download FileZila Server and install it to Drive other that Windows drive.
2. File->Connect to server let setting unchanged and click on Connect.Below Message will be diaplayed </br>
<pre>
You appear to be behind a NAT router. Please configure the passive mode settings and forward a range of ports in your router.
Warning: FTP over TLS is not enabled, users cannot securely log in.
</pre>

3. Edit->User  Click on add enter user name <spring>. Ckeck Enable Account  and Password to set password
   Shared Folder Add Physical path where we want to keep tranfered file.
