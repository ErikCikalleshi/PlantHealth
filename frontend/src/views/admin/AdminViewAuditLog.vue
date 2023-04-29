<template>
  <v-app>
    <header-component/>
    <main-container negative class="mb-10">
      <div class="flex center justify-space-between mb-10">
        <page-heading class="text-white">Audit Logs</page-heading>
          <div class="flex items-center">
            <div class="w-[220px]">
              <v-select
                  label="Select"
                  :items="['all', 'update', 'delete', 'create']"
                  v-model="filterAction"
                  @change="getAuditsByAction(filterAction)"
              ></v-select>
            </div>
          </div>
        </div>
      <div class="table-wrapper ">
      <table>
        <tr>
          <th v-for="item in this.headers" :key="item.text">
            {{ item.text }}
          </th>
        </tr>
      <tr v-for="item in filteredAudits" :key="item.id">
        <td> {{ item.id }} </td>
        <td> {{ formatAuditDate(item.date) }} </td>
        <td> {{ item.user }} </td>
        <td> {{ item.action }} </td>
        <td> {{ item.targetID }} </td>
        <td> {{ item.targetType }} </td>
        <td> {{ item.success }} </td>
      </tr>
      </table>
      </div>
    </main-container>
    <footer-component class="mt-auto"/>
  </v-app>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import AdminAuditLogService from "@/services/admin/AdminAuditLogService";
import headerComponent from "@/components/general/header.vue";
import footerComponent from "@/components/general/footer.vue";
import mainContainer from "@/components/general/main_container.vue";
import PageHeading from "@/components/general/PageHeading.vue";
import { format } from 'date-fns';
import type IAuditLog from "@/interfaces/IAuditLog";

export default defineComponent({
  name: "adminViewAuditLog",
  components: {
    headerComponent,
    footerComponent,
    mainContainer,
    PageHeading,
  },
  data() {
    return {
      audits: [] as IAuditLog[],
      filterAction: 'all',
      loading: false,
      headers: [
        { text: 'ID', value: 'id' },
        { text: 'Date', value: 'date' },
        { text: 'User', value: 'user' },
        { text: 'Action', value: 'action' },
        { text: 'TargetID', value: 'targetID' },
        { text: 'TargetType', value: 'targetType' },
        { text: 'Success', value: 'success' },
      ],
    }
  },
  methods: {
    format,
    getAllAuditLogs() {
      AdminAuditLogService.getAllAuditLogs().then((response) => {
        this.audits = response.data;
      })
    },
    getAuditsByAction(action: string) {
      AdminAuditLogService.getAuditLogsByAction(action).then((response) => {
        if (action == 'all') {
          this.getAllAuditLogs();
        } else {
          this.audits = response.data;
        }
      })
    },
    formatAuditDate(date: Date) {
      return format(new Date(date), 'dd/MM/yyyy HH:mm:ss');
    },
    },
  computed: {
    filteredAudits() {
      if (this.filterAction === "all") {
        return this.audits.reverse();
      } else {
        return this.audits.filter((audit) => audit.action === this.filterAction).reverse();
      }
    },
  },
  created() {
    this.getAllAuditLogs();
  },
  });
</script>

<style scoped>
table {
  width: 100%;
}
th, td {
  padding: 10px;
  text-align: left;
  border-bottom: 1px solid #ddd;
  color: black;
}

.table-wrapper {
  background-color: white;
  border: 1px solid #ddd;
  border-radius: 3px;
}
</style>