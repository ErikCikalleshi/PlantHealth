<script lang="ts">
import EmployeeService from '../services/EmployeeService';
import {defineComponent} from "vue";

export default defineComponent({
  name: "HelloWorld",
  data(){
    return {
      text: "Empty"
    }
  },
  watch: {
    "text": function(newValue, oldValue) {
      console.log(newValue);
    }
  },
  methods: {
    getEmployees(){
      EmployeeService.getEmployees().then((response) => {
        console.log("Test");
        console.log(response);
        this.text = response.data;
      });
    }
  },
  created() {
    this.getEmployees();
    EmployeeService.postMethod();
  }
});
</script>

<template>
  <div class="greetings">
    <h1 class="green">{{ text }}</h1>
    <h3>
      You’ve successfully created a project with
      <a href="https://vitejs.dev/" target="_blank" rel="noopener">Vite</a> +
      <a href="https://vuejs.org/" target="_blank" rel="noopener">Vue 3</a>. What's next?
    </h3>
  </div>
</template>


<style scoped>
h1 {
  font-weight: 500;
  font-size: 2.6rem;
  top: -10px;
}

h3 {
  font-size: 1.2rem;
}

.greetings h1,
.greetings h3 {
  text-align: center;
}

@media (min-width: 1024px) {
  .greetings h1,
  .greetings h3 {
    text-align: left;
  }
}
</style>
