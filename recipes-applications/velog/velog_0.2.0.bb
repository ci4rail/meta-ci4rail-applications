SUMMARY = "Vehicle Data Logger"
DESCRIPTION = ""
HOMEPAGE = "https://github.com/ci4rail/velog"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

COMPATIBLE_HOST = "(aarch64).*-linux"

SRC_URI_aarch64 = "https://github.com/ci4rail/mvb-can-logger/releases/download/v${PV}/${BPN}_logger-v${PV}-linux-arm64.tar.gz;name=arm64"
SRC_URI_append = " file://${BPN}.service"
SRC_URI_append = " file://velog_config.yaml"
SRC_URI_append = " file://velog.sh"

# Use github-release-checksums.sh script in yoct-images repo to update
SRC_URI[arm64.md5sum] = "7f236d4ec14c869cf530baba10e263f3"
SRC_URI[arm64.sha256sum] = "7edc607900e125877cc3e858794caa4ee95567142efbf43b76386f504f1db009"

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE_${BPN} = "${BPN}.service"
FILES_${PN} += "${systemd_system_unitdir}/${BPN}.service"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/${BPN}_logger ${D}${bindir}/${BPN}_logger
    install -m 0755 ${WORKDIR}/${BPN}.sh ${D}${bindir}/${BPN}
    install -d ${D}${sysconfdir}
    install -m 0644 ${WORKDIR}/velog_config.yaml ${D}${sysconfdir}/velog_config.yaml
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/${BPN}.service ${D}${systemd_unitdir}/system/${BPN}.service
}

FILES_${PN} += "${bindir}/${BPN}_logger"
FILES_${PN} += "${bindir}/${BPN}"
FILES_${PN} += "${sysconfdir}/velog_config.yaml"
FILES_${PN} += "${systemd_system_unitdir}/${BPN}.service"

inherit systemd
