# ew_snmp

A Clojure library to work with snmp4j.
So far based in https://github.com/maoe/snmp4clj and https://github.com/jlbfalcao/clojure-snmp

## Usage

Once upon a time...

http://stackoverflow.com/questions/3224687/getting-started-with-snmp4j

https://developer.apple.com/library/mac/documentation/Darwin/Reference/ManPages/man1/snmpwalk.1.html#//apple_ref/doc/man/1/snmpwalk

https://developer.apple.com/library/mac/documentation/Darwin/Reference/ManPages/man1/snmpget.1.html


http://www.macworld.com.au/help/setting-up-and-using-snmpv3-on-os-x-1212/

snmpwalk -Os -c public -v 1 localhost system

snmpget -c public localhost 1.3.6.1.2.1.0

snmpget -v 2c -c public localhost SNMPv2-MIB::sysUpTime.0

snmpget -v 2c -c public localhost SNMPv2-MIB::sysObjectID.0

sysDescr.0 = STRING: Darwin Mac-mini-de-juan-mini.local 11.4.2 Darwin Kernel Version 11.4.2: Thu Aug 23 16:25:48 PDT 2012; root:xnu-1699.32.7~1/RELEASE_X86_64 x86_64
sysObjectID.0 = OID: netSnmpAgentOIDs.255
sysUpTimeInstance = Timeticks: (535879) 1:29:18.79
sysContact.0 = STRING: Administrator <postmaster@example.com>
sysName.0 = STRING: Mac-mini-de-juan-mini.local
sysLocation.0 = STRING: Right here, right now.
sysServices.0 = INTEGER: 76
sysORLastChange.0 = Timeticks: (30) 0:00:00.30
sysORID.1 = OID: snmpMPDMIBObjects.3.1.1
sysORID.2 = OID: usmMIBCompliance
sysORID.3 = OID: snmpFrameworkMIBCompliance
sysORID.4 = OID: snmpMIB
sysORID.5 = OID: tcpMIB
sysORID.6 = OID: ip
sysORID.7 = OID: udpMIB
sysORID.8 = OID: vacmBasicGroup
sysORDescr.1 = STRING: The MIB for Message Processing and Dispatching.
sysORDescr.2 = STRING: The MIB for Message Processing and Dispatching.
sysORDescr.3 = STRING: The SNMP Management Architecture MIB.
sysORDescr.4 = STRING: The MIB module for SNMPv2 entities
sysORDescr.5 = STRING: The MIB module for managing TCP implementations
sysORDescr.6 = STRING: The MIB module for managing IP and ICMP implementations
sysORDescr.7 = STRING: The MIB module for managing UDP implementations
sysORDescr.8 = STRING: View-based Access Control Model for SNMP.
sysORUpTime.1 = Timeticks: (26) 0:00:00.26
sysORUpTime.2 = Timeticks: (26) 0:00:00.26
sysORUpTime.3 = Timeticks: (26) 0:00:00.26
sysORUpTime.4 = Timeticks: (30) 0:00:00.30
sysORUpTime.5 = Timeticks: (30) 0:00:00.30
sysORUpTime.6 = Timeticks: (30) 0:00:00.30
sysORUpTime.7 = Timeticks: (30) 0:00:00.30
sysORUpTime.8 = Timeticks: (30) 0:00:00.30


# example getting system (1.3.6.1.2.1.1) third value (3.0) (check above the third line beginning on walk)
snmpget -v 2c -c public localhost 1.3.6.1.2.1.1.3.0
DISMAN-EVENT-MIB::sysUpTimeInstance = Timeticks: (582165) 1:37:01.65


# example getting system (1.3.6.1.2.1.1) second value (2.0) (check above the second line beginning on walk)
snmpget -v 2c -c public localhost 1.3.6.1.2.1.1.2.0
SNMPv2-MIB::sysObjectID.0 = OID: NET-SNMP-MIB::netSnmpAgentOIDs.255


Configure snmp (server)
snmpconf -i


Enabling SNMP on MacOS X 10.6 (Snow Leopard)
http://forums.intermapper.com/viewtopic.php?p=3016

## stoping snmp
sudo launchctl unload -w /System/Library/LaunchDaemons/org.net-snmp.snmpd.plist

## starting snmp
sudo launchctl load -w /System/Library/LaunchDaemons/org.net-snmp.snmpd.plist

### after following this tutorial

snmpget -v 3 -u juanv3 -l authPriv -a MD5 -A comomolalagramola -x DES -X comomolalagramola localhost 1.3.6.1.2.1.1.2.0

Using user javav3 and phrases comomolalagramola
snmpwalk -v 3 -u juanv3 -l authPriv -a MD5 -A comomolalagramola -x DES -X comomolalagramola localhost .1
->>> a lot of output of this style
SNMPv2-MIB::sysDescr.0 = STRING: Darwin Vigor21 11.4.2 Darwin Kernel Version 11.4.2: Thu Aug 23 16:25:48 PDT 2012; root:xnu-1699.32.7~1/RELEASE_X86_64 x86_64
SNMPv2-MIB::sysObjectID.0 = OID: NET-SNMP-MIB::netSnmpAgentOIDs.255
DISMAN-EVENT-MIB::sysUpTimeInstance = Timeticks: (1922) 0:00:19.22
SNMPv2-MIB::sysContact.0 = STRING: Administrator <postmaster@example.com>
SNMPv2-MIB::sysName.0 = STRING: Vigor21
SNMPv2-MIB::sysLocation.0 = STRING: Right here, right now.
SNMPv2-MIB::sysServices.0 = INTEGER: 76
SNMPv2-MIB::sysORLastChange.0 = Timeticks: (0) 0:00:00.00
SNMPv2-MIB::sysORID.1 = OID: SNMP-MPD-MIB::snmpMPDMIBObjects.3.1.1
SNMPv2-MIB::sysORID.2 = OID: SNMP-USER-BASED-SM-MIB::usmMIBCompliance
SNMPv2-MIB::sysORID.3 = OID: SNMP-FRAMEWORK-MIB::snmpFrameworkMIBCompliance
SNMPv2-MIB::sysORID.4 = OID: SNMPv2-MIB::snmpMIB
SNMPv2-MIB::sysORID.5 = OID: TCP-MIB::tcpMIB
SNMPv2-MIB::sysORID.6 = OID: IP-MIB::ip
SNMPv2-MIB::sysORID.7 = OID: UDP-MIB::udpMIB
SNMPv2-MIB::sysORID.8 = OID: SNMP-VIEW-BASED-ACM-MIB::vacmBasicGroup
SNMPv2-MIB::sysORDescr.1 = STRING: The MIB for Message Processing and Dispatching.
SNMPv2-MIB::sysORDescr.2 = STRING: The MIB for Message Processing and Dispatching.
SNMPv2-MIB::sysORDescr.3 = STRING: The SNMP Management Architecture MIB.
SNMPv2-MIB::sysORDescr.4 = STRING: The MIB module for SNMPv2 entities
SNMPv2-MIB::sysORDescr.5 = STRING: The MIB module for managing TCP implementations
..... and more....


# using v3 snmp4j
http://www.snmp4j.org/doc/org/snmp4j/security/package-summary.html
http://stackoverflow.com/questions/6831964/snmp4j-adding-user


# generating and using clojure jar

$ lein compile
$ lein uberjar
$ cd HelloJava
$ cp ../target/ew_snmp-0.1.0-standalone.jar .
$ javac -cp '.:ew_snmp-0.1.0-standalone.jar' HelloJava.java
$ java -cp '.:ew_snmp-0.1.0-standalone.jar' HelloJava
>> Hello from Java!
>> Vigor21
>> Vigor21

## License

Copyright © 2014

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
