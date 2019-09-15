DESCRIPTION = "A generic image for network and communication."
LICENSE = "MIT"
PR = "r1"

require atmel-demo-image.inc


IMAGE_INSTALL += "beetle hubby af-sec af-ipc af-util af-edge attrd af-conn bluez5 bluez5-noinst-tools wpa-supplicant iptables iw linux-firmware-bcm43430 curl openssl ntp vim-common otamgr i2c-tools dropbear ntp"

IMAGE_FEATURES += " package-management"

#ROOTFS_POSTPROCESS_COMMAND += " afero_rpi3_generate_sysctl_config ; afero_rpi3_set_root_password ; afero_rpi3_set_build_version ;"

afero_rpi3_generate_sysctl_config() {
    # sysv sysctl config
    #
    IMAGE_SYSCTL_CONF="${IMAGE_ROOTFS}${sysconfdir}/sysctl.conf"
    test -e ${IMAGE_ROOTFS}${sysconfdir}/sysctl.conf && \
        sed -e "/net.ipv4.ip_forward/d" -i ${IMAGE_SYSCTL_CONF}
    echo "" >> ${IMAGE_SYSCTL_CONF} && echo "net.ipv4.ip_forward = 1" >> ${IMAGE_SYSCTL_CONF}
}

afero_rpi3_set_root_password() {
    sed -i 's/^root::/root:$6$K1WUAMxw$vqwdmzbg\/ANhNO7R9LHSP8CIELBt.j\/Jsda1X9FhvAckG1AVYqgS3bScGMNendRCUmUqJ.Mpm22KfmyuaTdCK0:/' ${IMAGE_ROOTFS}${sysconfdir}/shadow
}

afero_rpi3_set_build_version() {
    DEST_FILE="${IMAGE_ROOTFS}${sysconfdir}/app_ota_record.json"
    echo '{' > ${DEST_FILE}
    echo "    \"type\" : 3," >> ${DEST_FILE}
    echo "    \"versionNumber\" : \"${HUB_VERSION}\"" >> ${DEST_FILE}
    echo '}' >> ${DEST_FILE}
}


afero_modify_crontab() {
    CRONTAB_ROOT_FILE="${IMAGE_ROOTFS}${sysconfdir}/crontab"
    CHECK_AF_SERVICE_FILE="${IMAGE_ROOTFS}${bindir}/check-af-services.sh"
    #
    # Added the check-af-service.sh line
    #
    if [ -e $CRONTAB_ROOT_FILE ] ; then
        echo " *** Find the crontab file"
        if [ -e $CHECK_AF_SERVICE_FILE ] ; then
            echo " *** Find the check-af-services.sh file "

            echo -n >> ${CRONTAB_ROOT_FILE}
            echo "  *   *     * * *     root   /usr/bin/check-af-services.sh" >> ${CRONTAB_ROOT_FILE}
            echo " */15  *    * * *     root   /usr/bin/logpush" >> ${CRONTAB_ROOT_FILE}
        fi
    fi
}
