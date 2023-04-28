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
          <tr v-for="audit in filteredAudits" :key="audit.id">
            <td>{{ audit.id }}</td>
            <td>{{ audit.usernameModifier }}</td>
            <td>{{ audit.action }}</td>
            <td>{{ audit.timestamp }}</td>
          </tr>
          </tbody>
        </template>
      </v-simple-table>
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
      filterAction: 'all',
      loading: false,
    }
  },
  methods: {
    getAllAuditLogs() {
      AdminAuditLogService.getAllAuditLogs().then((response) => {
        this.audits = response.data;
      })
    },
    getAuditsByAction(action: string) {
      AdminAuditLogService.getAuditLogsByAction(action).then((response) => {
        if (action == 'all') {
          this.getAllAuditLogs();
        } else  {
          this.audits = response.data;
        }
      })
      }
    },
  computed: {
    filteredAudits() {
      if (this.filterAction === "all") {
        return this.audits;
      } else {
        return this.audits.filter((audit) => audit.action === this.filterAction);
      }
    },
  },
  created() {
    this.getAllAuditLogs();
  },
  });
</script>