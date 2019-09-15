# Copyright (C) 2017 Afero, Inc. All rights reserved

DESCRIPTION = "Afero Connectivity"
SECTION = "examples"
DEPENDS = "attrd af-ipc af-util libevent libpcap openssl af-sec"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

inherit externalsrc autotools update-rc.d
EXTERNALSRC = "${TOPDIR}/../../src_afero/af-conn/pkg"

EXTRA_OECONF = "BUILD_TARGET=${BUILD_TARGET}"

PARALLEL_MAKE = ""
SRC_URI += " file://af-conn"
SRC_URI += " file://af_conn_watcher"

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME = "af-conn"
INITSCRIPT_PARAMS = "defaults 90 10"

# install the files into the destination directory so it can be packaged
# correctly
do_install_append() {
    echo "do_install() af-conn directories and configuration files ...."

    install -d ${D}${bindir}
    install -d ${D}${sysconfdir}
    install -d ${D}${sysconfdir}/init.d/
    install -d ${D}${libdir}
    install -d ${D}${libdir}/af-conn/

    # migrated from rpi af-system: afero_nv is a partition to store Afero daemons' data
    # Note: partner needs to create afero_nv as a persistent partition so a system
    #       upgrade doesn't wipe out the data.
    install -d ${D}/data
    ln -s ../data ${D}/afero_nv


    install -Dm 755 ${WORKDIR}/af-conn ${D}${sysconfdir}/init.d/
    install -Dm 755 ${WORKDIR}/af_conn_watcher ${D}${libdir}/af-conn/

    # Install directories
    for i in `find ${EXTERNALSRC}/../files/sama5 -type d | sed 's/.*sama5//'` ; do
        install -d ${D}${i}
    done
    # Install files
    # TODO - some of the files may need to be be removed (and given to customer)
    #        for customization: afero_net_cap, afero_netif_names,etc
    for i in `find ${EXTERNALSRC}/../files/sama5 -type f | sed 's/.*sama5//'` ; do
        cp ${EXTERNALSRC}/../files/sama5${i} ${D}${i}
    done
    # Install symlinks
    for i in `find ${EXTERNALSRC}/../files/sama5 -type l | sed 's/.*sama5//'` ; do
        cp -dp ${EXTERNALSRC}/../files/sama5${i} ${D}${i}
    done

    if [ "x${BUILD_TYPE}x" = "xdevx" ] ; then
        cp ${D}${sysconfdir}/af-conn/whitelist.dev ${D}${sysconfdir}/af-conn/whitelist
    else
        cp ${D}${sysconfdir}/af-conn/whitelist.prod ${D}${sysconfdir}/af-conn/whitelist
    fi
    rm ${D}${sysconfdir}/af-conn/whitelist.dev
    rm ${D}${sysconfdir}/af-conn/whitelist.prod

    # fix the init_firewall.sh file for release targets
#    if [ "x${BUILD_TARGET}x" = "xreleasex" ] ; then
    #    cat ${D}${libdir}/af-conn/init_firewall.sh | sed -e ':a;N;$!ba;s/\\\n\s*/ /g' | grep -iv remove > ${WORKDIR}/firewall.release
    #    rm ${D}${libdir}/af-conn/init_firewall.sh
    #    install -Dm 755 ${WORKDIR}/firewall.release ${D}${libdir}/af-conn/init_firewall.sh
#    fi
}

FILES_${PN} += " ./usr/lib/af-conn/* ./etc/af-conn/* ./data ./afero_nv"
