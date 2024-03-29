# Copyright (C) 2017 Afero, Inc. All rights reserved

DESCRIPTION = "Afero security daemon and library"
SECTION = "examples"
DEPENDS = "af-ipc af-util openssl libevent"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = ""

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

inherit update-rc.d
EXTRA_OECONF = "BUILD_TYPE=${BUILD_TYPE} BUILD_TARGET=${BUILD_TARGET}"
PARALLEL_MAKE = ""

SRC_URI = "git://git@github.com/AferoCE/sama5-binaries-af-sec;protocol=ssh"
SRCREV = "8199d39c517285114fd5d26b67bf67aa337e269c"
SRC_URI += " file://afsecd"
SRC_URI += " file://afsecd_watcher"

S = "${WORKDIR}/git"

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME = "afsecd"
INITSCRIPT_PARAMS = "defaults 60 40"

do_install_append() {
    echo "do_install() af-sec directories and configuration files ...."

	# install libafwp.so
    install -d ${D}${libdir}
    install -Dm 755 ${S}/${BUILD_TARGET}${libdir}/libafwp.a ${D}${libdir}

    # install afwp.h
    install -d ${D}${includedir}
    install -Dm 644 ${S}/${BUILD_TARGET}${includedir}/afwp.h ${D}${includedir}

    # install afsecd and provision
    install -d ${D}${bindir}
    install -Dm 755 ${S}/${BUILD_TARGET}${bindir}/afsecd ${D}${bindir}
    install -Dm 755 ${S}/${BUILD_TARGET}${bindir}/provision ${D}${bindir}

    # install the init.d script for afsecd, and watcher
    install -d ${D}${sysconfdir}/init.d/
    install -Dm 755 ${WORKDIR}/afsecd ${D}${sysconfdir}/init.d/
    install -Dm 755 ${WORKDIR}/afsecd_watcher ${D}${bindir}
}
