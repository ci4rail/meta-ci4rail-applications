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

# Use github-release-checksums.sh script in yocto-images repo to update
SRC_URI[arm64.md5sum] = "f55ef3a5f0d295df8402cdee331352c6"
SRC_URI[arm64.sha256sum] = "9db56e62976c6c276af72ba9eb522462e5cfc9a62632f0e9a3bd10f4f1b2f29e"

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
RDEPENDS_${PN} = "bash"

FILES_${PN} += "${bindir}/${BPN}_logger"
FILES_${PN} += "${bindir}/${BPN}"
FILES_${PN} += "${sysconfdir}/velog_config.yaml"
FILES_${PN} += "${systemd_system_unitdir}/${BPN}.service"

inherit systemd
