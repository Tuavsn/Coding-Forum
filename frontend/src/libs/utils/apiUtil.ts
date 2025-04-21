export const ApiUtil = {
    getPublicData: async (url: string) => {
        const headers = {
            'Content-Type': 'application/json'
        };
        const results = await fetch(url, {
            headers: headers
        });
        return results.json();
    },
    getData: async (url: string) => {
        const token = localStorage.getItem('accessToken') || undefined;
        const headers = {
            'Content-Type': 'application/json',
            ...(token && { 'Authorization': `Bearer ${token}` })
        };
        const results = await fetch(url, {
            method: 'GET',
            headers: headers,
        });
        return results.json();
    },
    postData: async (url: string, post: Object) => {
        const token = localStorage.getItem('accessToken') || undefined;
        const headers = {
            'Content-Type': 'application/json',
            ...(token && { 'Authorization': `Bearer ${token}` })
        };
        const results = await fetch(url, {
            method: 'POST',
            headers: headers,
            body: JSON.stringify(post)
        });
        return results.json();
    },
    putData: async (url: string, put: Object) => {
        const token = localStorage.getItem('accessToken') || undefined;
        const headers = {
            'Content-Type': 'application/json',
            ...(token && { 'Authorization': `Bearer ${token}` })
        };
        const results = await fetch(url, {
            method: 'PUT',
            headers: headers,
            body: JSON.stringify(put)
        })
        return results.json();
    },
    patchData: async (url: string, patch: Object) => {
        const token = localStorage.getItem('accessToken') || undefined;
        const headers = {
            'Content-Type': 'application/json',
            ...(token && { 'Authorization': `Bearer ${token}` })
        };
        const results = await fetch(url, {
            method: 'PUT',
            headers: headers,
            body: JSON.stringify(patch)
        })
        return results.json();
    },
    deleteData: async (url: string) => {
        const token = localStorage.getItem('accessToken') || undefined;
        const headers = {
            'Content-Type': 'application/json',
            ...(token && { 'Authorization': `Bearer ${token}` })
        };
        const results = await fetch(url, {
            method: 'DELETE',
            headers: headers
        });
        return results.json();
    }
}