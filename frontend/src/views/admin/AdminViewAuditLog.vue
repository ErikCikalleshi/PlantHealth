<template>
    <v-app>
        <header-component/>
        <main-container negative class="mb-10">
            <div class="flex center justify-space-between mb-10">
                <page-heading class="text-white">Audit Logs</page-heading>
                <div class="flex items-center">
                    <div class="w-[220px]">
                <v-select
                        :items="['all', 'create', 'update', 'delete']"
                        v-model="selectedAction"
                        label="Filter Action"
                />
                    </div>
                </div>
            </div>
                <EasyDataTable
                    :headers="headers"
                    :key="items.length"
                    :items="filteredItems"
                    :alternating="true">
                <template #item-date="item">
                    {{ formatAuditDate(item.date) }}
                </template>

            </EasyDataTable>
        </main-container>
        <footer-component class="mt-auto"/>
    </v-app>
</template>


<script lang="ts">
import {computed, defineComponent, ref} from "vue";
import AdminAuditLogService from "@/services/admin/AdminAuditLogService";
import headerComponent from "@/components/general/header.vue";
import footerComponent from "@/components/general/footer.vue";
import mainContainer from "@/components/general/main_container.vue";
import PageHeading from "@/components/general/PageHeading.vue";
import { format } from 'date-fns';
import type IAuditLog from "@/interfaces/IAuditLog";
import type { Header, Item } from "vue3-easy-data-table";

export default defineComponent({
    name: "adminViewAuditLog",
    components: {
        headerComponent,
        footerComponent,
        mainContainer,
        PageHeading,
    },
    setup() {
        let auditLog = ref<IAuditLog>({
            id: 0,
            date: new Date(),
            user: '',
            action: '',
            targetID: '',
            targetType: '',
            success: false,
        });
        let items = ref<Item[]>([]);
        AdminAuditLogService.getAllAuditLogs().then((response) => {
            auditLog = response.data;
            items.value = response.data;
            return {
                auditLog,
                items,
            }
        });
        const headers: Header[] = [
            { text: 'ID', value: 'id', sortable: true },
            { text: 'Date', value: 'date', sortable: true},
            { text: 'User', value: 'user', sortable: true },
            { text: 'Action', value: 'action', sortable: true},
            { text: 'TargetID', value: 'targetID', sortable: true },
            { text: 'TargetType', value: 'targetType', sortable: true},
            { text: 'Success', value: 'success', sortable: true},
        ];
        const selectedAction = ref('all');

        const filteredItems = computed(() => {
            if (selectedAction.value === 'all') {
                return items.value;
            }
            return items.value.filter((item: Item) => item.action === selectedAction.value.toLowerCase());
        });

        return {
            auditLog,
            headers,
            items,
            selectedAction,
            filteredItems,
        }
    },
    methods: {
        format,
        formatAuditDate(date: Date) {
            return format(new Date(date), 'dd/MM/yyyy HH:mm:ss');
        },
    },
});
</script>

