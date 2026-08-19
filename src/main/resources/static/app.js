const API_BASE_URL = 'http://localhost:8080/api/v1';

// 1. Register Account Logic
document.getElementById('btnRegister').addEventListener('click', async () => {
    const ownerName = document.getElementById('ownerName').value;
    const aliasValue = document.getElementById('aliasValue').value;
    const responseDiv = document.getElementById('registerResponse');

    try {
        const response = await fetch(`${API_BASE_URL}/accounts`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                ownerName: ownerName,
                aliasValue: aliasValue,
                keyType: 'EMAIL'
            })
        });

        const data = await response.json();
        responseDiv.classList.remove('hidden', 'bg-red-50', 'text-red-600');

        if (response.ok) {
            responseDiv.innerHTML = `Success! Account ID: <strong>${data.id}</strong>`;
        } else {
            responseDiv.classList.add('bg-red-50', 'text-red-600');
            responseDiv.innerHTML = `Error: ${data.error || 'Could not create account'}`;
        }
    } catch (error) {
        responseDiv.classList.remove('hidden');
        responseDiv.innerHTML = `Connection error. Is the server running?`;
    }
});

// 2. Check Balance Logic
document.getElementById('btnCheckBalance').addEventListener('click', async () => {
    const accountId = document.getElementById('balanceAccountId').value;
    const responseDiv = document.getElementById('balanceResponse');

    try {
        const response = await fetch(`${API_BASE_URL}/ledger/${accountId}/balance`);
        const data = await response.json();

        console.log("API Response from Java: ", data);

        responseDiv.classList.remove('hidden');

        if (response.ok) {
            responseDiv.innerHTML = `Balance: $${data.currentBalance}`;
        } else {
            responseDiv.innerHTML = `Error retrieving balance.`;
        }
    } catch (error) {
        responseDiv.classList.remove('hidden');
        responseDiv.innerHTML = `Connection error.`;
    }
});

// 3. Execute Transfer Logic
document.getElementById('btnTransfer').addEventListener('click', async () => {
    const fromAccount = document.getElementById('fromAccount').value;
    const toAccount = document.getElementById('toAccount').value;
    const amount = document.getElementById('transferAmount').value;
    const responseDiv = document.getElementById('transferResponse');

    // Auto-generating a timestamp-based idempotency key for the UI
    const idempotencyKey = 'transfer-ui-' + Date.now();

    try {
        const response = await fetch(`${API_BASE_URL}/transactions/transfer`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                fromAccount: fromAccount,
                toAccount: toAccount,
                amount: parseFloat(amount),
                idempotencyKey: idempotencyKey
            })
        });

        responseDiv.classList.remove('hidden', 'bg-red-50', 'text-red-600', 'bg-emerald-50', 'text-emerald-700');

        if (response.ok) {
            responseDiv.classList.add('bg-emerald-50', 'text-emerald-700');
            responseDiv.innerHTML = `Transfer Successful! 🚀 Check balances to verify.`;
        } else {
            // Since we don't have @ControllerAdvice yet, we handle the 500 error gracefully
            responseDiv.classList.add('bg-red-50', 'text-red-600');
            responseDiv.innerHTML = `Transfer Failed: Insufficient funds or invalid details.`;
        }
    } catch (error) {
        responseDiv.classList.remove('hidden');
        responseDiv.classList.add('bg-red-50', 'text-red-600');
        responseDiv.innerHTML = `Connection error.`;
    }
});

// Deposit Logic
document.getElementById('btnDeposit').addEventListener('click', async () => {
    const accountId = document.getElementById('depositAccountId').value;
    const amount = document.getElementById('depositAmount').value;
    const responseDiv = document.getElementById('depositResponse');

    // Auto-generating a timestamp-based idempotency key for the UI
    const idempotencyKey = 'deposit-ui-' + Date.now();

    try {
        const response = await fetch('http://localhost:8080/api/v1/transactions/deposit', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                accountId: accountId,
                amount: parseFloat(amount),
                idempotencyKey: idempotencyKey
            })
        });

        responseDiv.classList.remove('hidden', 'bg-red-50', 'text-red-600', 'bg-emerald-50', 'text-emerald-700');

        if (response.ok) {
            responseDiv.classList.add('bg-emerald-50', 'text-emerald-700');
            responseDiv.innerHTML = `Deposit Successful! 💸`;
        } else {
            responseDiv.classList.add('bg-red-50', 'text-red-600');
            responseDiv.innerHTML = `Deposit Failed. Check your UUID.`;
        }
    } catch (error) {
        responseDiv.classList.remove('hidden');
        responseDiv.classList.add('bg-red-50', 'text-red-600');
        responseDiv.innerHTML = `Connection error. Is the Spring Boot server running?`;
    }
});