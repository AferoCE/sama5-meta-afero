FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

BCM_BT_SOURCES =  " \
    file://BCM43430A1.hcd \
    file://brcm43438.service \
    "

enable_bcm_bluetooth() {

    # create the /etc/firmware dire
    install -d ${D}${sysconfdir}
    install -d ${D}${sysconfdir}/firmware

    install -m 0644 ${WORKDIR}/BCM43430A1.hcd ${D}/${sysconfdir}/firmware/BCM43430A1.hcd

    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -d ${D}${systemd_unitdir}/system
        install -m 0644 ${WORKDIR}/brcm43438.service ${D}${systemd_unitdir}/system
    fi
}

BCM_BT_FIRMWARE =  " \
    /etc/firmware/BCM43430A1.hcd \
    "

#BCM_BT_SERVICE =  " brcm43438.service"

# for raspberrypi3
#SRC_URI_append_raspberrypi3 = " ${BCM_BT_SOURCES}"


SRC_URI += " ${BCM_BT_SOURCES}"

do_install_append() {
    enable_bcm_bluetooth
}

#FILES_${PN}_append_raspberrypi3 = " ${BCM_BT_FIRMWARE}"
FILES_${PN} += " ${BCM_BT_FIRMWARE}"

#SYSTEMD_SERVICE_${PN} += " ${BCM_BT_SERVICE}"

