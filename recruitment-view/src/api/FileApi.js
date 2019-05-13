import {ApiUtil} from '@/util/Util';

import OSS from 'ali-oss';

const FileApi = {
  uploadFile: async (params) => {
    return await ApiUtil.post('/oss', params);
  },
  uploadDirectOss: (fileName, content, env) => {
    const base64 = content.split(',').pop();
    const fileType = content.split(';').shift().split(':').pop();
    const blob = FileApi.toBlob(base64, fileType);
    const reader = new FileReader();
    reader.readAsArrayBuffer(blob);
    return new Promise((resolve, reject) => {
      reader.onload = function (event) {
        let client = new OSS({
          accessKeyId: env.VUE_APP_ALI_ACCESS_KEY_ID,
          accessKeySecret: env.VUE_APP_ALI_ACCESS_KEY_SECRET,
          bucket: env.VUE_APP_ALI_BUCKET,
          endpoint: env.VUE_APP_ALI_ENDPOINT
        });
        const buffer = new OSS.Buffer(event.target.result);
        client.put("web/" + fileName, buffer).then(result => {
          resolve(result);
        }).catch(e => {
          reject(e);
        });
      }
    });
  },
  toBlob: (urlData, fileType) => {
    let bytes = window.atob(urlData);
    let n = bytes.length;
    let u8arr = new Uint8Array(n);
    while (n--) {
      u8arr[n] = bytes.charCodeAt(n);
    }
    return new Blob([u8arr], {type: fileType});
  }
};

export default FileApi;
