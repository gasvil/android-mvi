package com.example.reports.data.repository

import com.example.reports.domain.model.Operation
import com.example.reports.domain.model.OperationType
import com.example.reports.domain.repository.ReportsRepository
import kotlinx.coroutines.delay
import java.util.UUID
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReportsRepositoryImpl @Inject constructor() : ReportsRepository {

    override suspend fun getOperations(): Result<List<Operation>> {
        delay(1200L)
        return Result.success(fakeFeedOperations())
    }

    private fun daysAgoMillis(days: Int): Long =
        System.currentTimeMillis() - TimeUnit.DAYS.toMillis(days.toLong())

    private fun fakeFeedOperations(): List<Operation> = listOf(
        Operation(UUID.randomUUID().toString(), "Salary Deposit", "Monthly salary payment", 3_500.00, OperationType.CREDIT, daysAgoMillis(1)),
        Operation(UUID.randomUUID().toString(), "Netflix Subscription", "Monthly subscription renewal", 15.99, OperationType.DEBIT, daysAgoMillis(2)),
        Operation(UUID.randomUUID().toString(), "Transfer to John", "Shared rent payment", 600.00, OperationType.TRANSFER, daysAgoMillis(3)),
        Operation(UUID.randomUUID().toString(), "Grocery Store", "Weekly groceries", 87.45, OperationType.DEBIT, daysAgoMillis(4)),
        Operation(UUID.randomUUID().toString(), "Freelance Project", "UI design project payment", 1_200.00, OperationType.CREDIT, daysAgoMillis(5)),
        Operation(UUID.randomUUID().toString(), "Electricity Bill", "Monthly utility payment", 75.20, OperationType.DEBIT, daysAgoMillis(6)),
        Operation(UUID.randomUUID().toString(), "Transfer from Savings", "Emergency fund withdrawal", 200.00, OperationType.TRANSFER, daysAgoMillis(7)),
        Operation(UUID.randomUUID().toString(), "Amazon Purchase", "Electronics accessories", 49.99, OperationType.DEBIT, daysAgoMillis(8))
    )
}

