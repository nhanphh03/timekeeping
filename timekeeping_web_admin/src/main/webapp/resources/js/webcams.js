(function () {
    var Webcams = window.Webcams || {};

    Webcams.init = function (callback) {
        callback = callback || function () {
        };

        this.getDevices(function (devices) {
            // Request permission
            if (devices[0] && !devices[0].label) {
                navigator.mediaDevices.getUserMedia({video: true}).then(function (stream) {
                    var video;
                    try {
                        video = document.createElement('video');
                        video.muted = true;
                        video.volume = 0;
                        video.src = URL.createObjectURL(stream);
                        video.style.display = 'none';
                        video.style.opacity = 0;
                        (document.body || document.documentElement).appendChild(video);
                    } catch (e) {
                    }

                    stream.getTracks().forEach(function (track) {
                        track.stop();
                    });

                    if (video && video.parentNode) {
                        video.parentNode.removeChild(video);
                    }
                }).catch();
            }

            //
            if (callback) {
                callback();
            }
        });
    };

    Webcams.getDevices = function (callback) {
        navigator.mediaDevices.enumerateDevices().then(devices => {
            const filtered = devices.filter(device => device.kind === 'videoinput');
            callback(filtered);
        });
    }

    Webcams.hasPermission = function (callback) {
        this.getDevices(function (devices) {
            if (devices[0] && !devices[0].label) {
                callback(false);
            } else {
                callback(true);
            }
        });
    }

    Webcams.create = function () {
        return this.create('');
    }

    Webcams.create = function (deviceId) {
        webcam = new Webcam();
        webcam.deviceId = deviceId;
        return webcam;
    }
    Webcams.create = function (w, h) {
        webcam = new Webcam();
        webcam.width = w;
        webcam.height = h;
        return webcam;
    }
    Webcams.create = function (deviceId, w, h) {
        webcam = new Webcam();
        webcam.deviceId = deviceId;
        webcam.width = w;
        webcam.height = h;
        return webcam;
    }
    window.Webcams = Webcams;


    function Webcam() {
        this.deviceId = "";
        this.width = 300;
        this.height = 225;

        this.setWidth = function (width) {
            this.width = width;
        }
        this.setHeight = function (height) {
            this.height = height;
        }

        this.attach = function (elem, callback) {
            callback = callback || function () {
            };
            if (typeof (elem) == 'string') {
                elem = document.getElementById(elem) || document.querySelector(elem);
            }

            elem.innerHTML = '';

            let video = document.createElement('video');
            video.setAttribute('autoplay', 'autoplay');
            video.setAttribute('playsinline', 'playsinline');
            if (typeof this.width == 'string') {
                video.style.width = this.width;
            } else {
                video.style.width = this.width + 'px';
            }
            if (typeof this.height == 'string') {
                video.style.height = this.height;
            } else {
                video.style.height = this.height + 'px';
            }

            elem.appendChild(video);
            this.video = video;

            this.changeDeviceId();
        }

        this.changeDeviceId = function (deviceId) {
            if (deviceId != undefined) {
                this.deviceId = deviceId;
            }

            //Stop current stream
            if (this.stream) {
                if (this.stream.getVideoTracks) {
                    // get video track to call stop on it
                    var tracks = this.stream.getVideoTracks();
                    if (tracks && tracks[0] && tracks[0].stop) tracks[0].stop();
                } else if (this.stream.stop) {
                    // deprecated, may be removed in future
                    this.stream.stop();
                }
            }

            var self = this;

            const constraints = {
                video: {
                    deviceId: this.deviceId ? {exact: this.deviceId} : undefined,
                    width: 1280,
                    height: 720
                }
            };
            return navigator.mediaDevices.getUserMedia(constraints).then(function (stream) {
                self.stream = stream;
                if ("srcObject" in self.video) {
                    self.video.srcObject = stream;
                } else {
                    self.video.src = window.URL.createObjectURL(stream);
                }
            }).catch(function (err) {
                console.log(err);
            });
        }

        this.snapshot = function (callback, width, height) {
            callback = callback || function () {
            };
            width = width || this.video.videoWidth;
            height = height || this.video.videoHeight;

            var canvas = document.createElement('canvas');
            canvas.width = width;
            canvas.height = height;
            console.log(width + "----" + height);
            var context = canvas.getContext('2d');
            context.drawImage(this.video, 0, 0, width, height);

            const base64 = canvas.toDataURL("image/jpeg", 0.99).split(';base64,')[1];
            callback(base64);
        }

        return this;
    }

})();