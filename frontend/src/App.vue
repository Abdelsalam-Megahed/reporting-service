<script>
import axios from "axios";

export default {
  data() {
    return {
      products: [],
      form: {
        productId: "",
        quantity: "",
        note: "",
      },
      disableDownloadButton: false,
      error: "",
    };
  },
  mounted() {
    this.fetchProducts();
  },
  methods: {
    fetchProducts() {
      axios
          .get("http://localhost:8080/api/v1/products")
          .then(({data}) => {
            this.products = data;
          })
          .catch(({response}) => {
            this.error = response?.data?.error;
          });
    },
    downloadReport() {
      this.disableDownloadButton = true;

      axios
          .post("http://localhost:8080/api/v1/generate-report", this.form, {
            responseType: "blob",
          })
          .then(({data}) => {
            let url = window.URL.createObjectURL(data);
            let a = document.createElement("a");
            a.href = url;
            a.download = "report_" + Date.now() + ".pdf";
            a.click();
            a.remove();
            setTimeout(() => window.URL.revokeObjectURL(url), 100);
          })
          .catch(({response}) => {
            this.error = response?.data?.error;
            this.disableDownloadButton = false;
          });

      this.disableDownloadButton = false;
    },
  }
}
</script>

<template>
  <div class="form-container">
    <h2>Create your report</h2>
    <form @submit.prevent="downloadReport">
      <div class="input-container">
        <input type="text" v-model="form.productId" required/>
        <label>Product name</label>
      </div>
      <div class="input-container">
        <input type="number" v-model="form.quantity" required min="1"/>
        <label>Quantity</label>
      </div>
      <div class="input-container">
        <input type="text" v-model="form.note"/>
        <label>Note</label>
      </div>

      <button type="submit" :disabled="disableDownloadButton">
        Generate and download
      </button>

      <div class="error-message" v-if="error">{{ error }}</div>
    </form>
  </div>
</template>

<style>
html {
  height: 100%;
}

body {
  margin: 0;
  padding: 0;
  font-family: sans-serif;
  background: #f0eeed;
}

.form-container {
  position: absolute;
  top: 50%;
  left: 50%;
  width: 400px;
  padding: 40px;
  transform: translate(-50%, -50%);
  background: rgba(0, 0, 0, 0.5);
  box-sizing: border-box;
  box-shadow: 0 15px 25px rgba(0, 0, 0, 0.6);
  border-radius: 10px;
}

.form-container h2 {
  margin: 0 0 30px;
  padding: 0;
  color: #fff;
  text-align: center;
}

.form-container .input-container {
  position: relative;
}

.form-container .input-container input {
  width: 100%;
  padding: 10px 0;
  font-size: 16px;
  color: #fff;
  margin-bottom: 30px;
  border: none;
  border-bottom: 1px solid #fff;
  outline: none;
  background: transparent;
}

.form-container .input-container label {
  position: absolute;
  top: -24px;
  left: 0;
  padding: 10px 0;
  font-size: 16px;
  color: #e8d5c4;
  pointer-events: none;
  transition: 0.5s;
}

button {
  padding: 10px 20px;
  color: #e8d5c4;
  font-size: 18px;
  text-decoration: none;
  overflow: hidden;
  transition: 0.5s;
  margin-top: 32px;
  letter-spacing: 3px;
  border: none;
  border-radius: 5px;
}

button:hover {
  background: #e8d5c4;
  color: #fff;
}

button:disabled {
  background: #eeeeee;
}

.message {
  color: #fff;
  margin-top: 32px;
  font-size: 18px;
}

.error-message {
  color: #f94a29;
  margin-top: 32px;
}
</style>
