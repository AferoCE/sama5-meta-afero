# Copyright (C) 2017 Afero, Inc. All rights reserved

DESCRIPTION = "Afero hub application"
SECTION = "examples"
DEPENDS = "beetle libevent json-c zlib openssl curl af-sec af-ipc af-util af-conn af-edge attrd"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

#Tina: using the binaries repo instead source
#inherit externalsrc update-rc.d autotools
#EXTERNALSRC = "${TOPDIR}/../../src_afero/hubby/pkg"
SRC_URI = "git://git@github.com/AferoCE/sama5-binaries-hubby;protocol=ssh"
SRCREV = "a2a7b1c3ae132f02b9d792bc38485aab006f3bc3"

S = "${WORKDIR}/git"

#EXTRA_OECONF = "BUILD_TYPE=${BUILD_TYPE} BUILD_NUMBER=${BUILD_NUMBER} HUBBY_BLUETOOTH=bluez"
#PARALLEL_MAKE = ""


SRC_URI += " file://hubby"
SRC_URI += " file://hubby_watcher"

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME = "hubby"
INITSCRIPT_PARAMS = "defaults 95 5"

do_install_append() {
    echo "do_install() hubby directories and configuration files ...."

    # install the profile
    install -d ${D}${sysconfdir}
    install -Dm 644 ${S}/${BUILD_TARGET}${sysconfdir}/hub.profile ${D}${sysconfdir}

    # install hubby daemon (binary)
    install -d ${D}${bindir}
    install -Dm 755 ${S}/${BUILD_TARGET}${bindir}/hubby ${D}${bindir}

    # install hubby init script and watcher
    install -d ${D}${sysconfdir}/init.d/
    install -Dm 755 ${WORKDIR}/hubby  ${D}${sysconfdir}/init.d/
    install -Dm 755 ${WORKDIR}/hubby_watcher  ${D}${bindir}
}