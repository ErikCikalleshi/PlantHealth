<template>
  <v-app>
    <header-component/>
    <main-container negative class="mb-10">
      <div class="flex center justify-space-between mb-10">
        <page-heading class="text-white">Audit Logs</page-heading>
          <div class="flex items-center">
            <div class="mx-2">
              <v-text-field
                  label="Filter by action"
                  v-model="filterAction"
                  @change="getAuditsByAction(filterAction)"
              ></v-text-field>
            </div>
            <v-simple-table>
              <template>
                <thead>
                <tr>
                  <th class="text-left">ID</th>
                  <th class="text-left">User</th>
                  <th class="text-left">Action</th>
                  <th class="text-left">Time</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="audit in auditsList" :key="audit.id">
                  <td>{{ audit.id }}</td>
                  <td>{{ audit.usernameModifier }}</td>
                  <td>{{ audit.action }}</td>
                  <td>{{ audit.timestamp }}</td>
                </tr>
                </tbody>
              </template>
            </v-simple-table>
          </div>
        </div>
    </main-container>
    <footer-component/>
  </v-app>
</template>

<script lang="ts">
import {defineComponent} from "vue";
import AdminAuditLogService from "@/services/admin/AdminAuditLogService";
import headerComponent from "@/components/general/header.vue";
import footerComponent from "@/components/general/footer.vue";
import mainContainer from "@/components/general/main_container.vue";
import PageHeading from "@/components/general/PageHeading.vue";
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
      filterAction: '',
      loading: false,
    }
  },
  methods: {
    getAllAuditLogs() {
      AdminAuditLogService.getAllAuditLogs().then((response) => {
        this.audits = response.data;
      })
    },
    getAuditsByAction(action: String) {
      AdminAuditLogService.getAuditLogsByAction(action).then((response) => {
        this.audits = response.data;
      })
      }
    },
  created() {
    this.getAllAuditLogs();
    this.getAuditsByAction(this.filterAction);
  },
  });
</script>