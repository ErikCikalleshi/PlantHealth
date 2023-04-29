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
      <EasyDataTable
          :loading="loading"
          :headers="headers"
          :items="filteredAudits"
          :alternating="true">
        <template #item.id="{ item }">
          {{ item.id }}
        </template>
        <template #item.date="{ item }">
          {{ item.date }}
        </template>
        <template #item.user="{ item }">
          {{ item.user }}
        </template>
        <template #item.action="{ item }">
          {{ item.action }}
        </template>
        <template #item.targetID="{ item }">
          {{ item.targetID }}
        </template>
        <template #item.targetType="{ item }">
          {{ item.targetType }}
        </template>
        <template #item.success="{ item }">
          {{ item.success }}
        </template>

        </EasyDataTable>
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