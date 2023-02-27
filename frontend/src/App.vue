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
      if (!this.isFormValid()) {
        return;
      }

      this.error = "";
      this.disableDownloadButton = true;

      axios
          .post("http://localhost:8080/api/v1/generate-report", this.form, {
            responseType: "blob",
          })
          .then(({data}) => {
            let url = window.URL.createObjectURL(data);
            let a = document.createElement("a");
            a.href = url;
            a.download = "report.pdf";
            a.click();
            a.remove();
            setTimeout(() => window.URL.revokeObjectURL(url), 100);
          })
          .catch(async (error) => {
            let responseObj = await error.response.data.text();
            let response = JSON.parse(responseObj);
            this.error = response?.error;
            this.disableDownloadButton = false;
          });

      this.disableDownloadButton = false;
    },
    isFormValid() {
      if (!this.form.productId) {
        this.error = "Please select a product!";
        return false;
      }

      if (this.form.quantity === "") {
        this.error = "Please set a quantity!";
        return false;
      }

      if (this.form.quantity < 1) {
        this.error = "Quantity must be greater than or equal to 1!";
        return false;
      }

      if (this.form.quantity > 10000) {
        this.error = "Quantity must be less than or equal to 10000!";
        return false;
      }

      return true;
    },
    selectProduct(id) {
      this.form.productId = id;
    },
  },
};
</script>

<template>
  <div class="form-container">
    <h2>Create your report</h2>
    <form @submit.prevent="downloadReport">
      <div class="dropdown-container">
        <p>Product name</p>
        <div
            class="option"
            :class="{ active: product.id === form.productId }"
            v-for="product in products"
            :key="product.id"
            @click="selectProduct(product.id)"
        >
          {{ product.name }}
        </div>
      </div>

      <div class="input-container">
        <input type="number" v-model="form.quantity"/>
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
  background: #4d455d;
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
  color: #f5e9cf;
  text-align: center;
}

.form-container p {
  padding: 10px 0;
  font-size: 16px;
  color: #f5e9cf;
}

.dropdown-container {
  margin-top: 4px;
  margin-bottom: 32px;
}

.option {
  padding: 8px 4px;
  margin-bottom: 8px;
  color: #f5e9cf;
  border: 1px solid #fff;
  border-radius: 4px;
  font-size: 14px;
}

.active {
  border: 2px solid #7db9b6;
}

.option:hover {
  cursor: pointer;
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
  color: #f5e9cf;
}

button {
  padding: 10px 20px;
  color: #7db9b6;
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
  background: #7db9b6;
  color: #fff;
  cursor: pointer;
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
  color: #e96479;
  margin-top: 32px;
}
</style>
