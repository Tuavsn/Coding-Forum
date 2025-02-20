const server_url = process.env.NEXT_PUBLIC_API_URL || 'https://codingforumapi.trinhhoctuan.io.vn'

export const getPublicData = async(url: string) => {
    const headers = {
        'Content-Type': 'application/json',
    };
    const results = await fetch(`${server_url}/${url}`, {
        method: 'GET',
        headers: headers,
        cache: "no-store"
    });
    return results.json();
}

export const getData = async(url: string) => {
    const token = localStorage.getItem('userToken') || undefined;
    const headers = {
        'Content-Type': 'application/json',
        ...(token && { 'Authorization': `Bearer ${token}` })
    };
    const result = await fetch(`${server_url}/${url}`, {
        method: 'GET',
        headers: headers,
    })
    return result.json();
}

export const postData = async(url: string, post: object) => {
    const token = localStorage.getItem('userToken') || undefined;
    const headers = {
        'Content-Type': 'application/json',
        ...(token && { 'Authorization': `Bearer ${token}` })
    };
    const result = await fetch(`${server_url}/${url}`, {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(post)
    })
    return result.json();
}

export const putData = async(url: string, put: object) => {
    const token = localStorage.getItem('userToken') || undefined;
    const headers = {
        'Content-Type': 'application/json',
        ...(token && { 'Authorization': `Bearer ${token}` })
    };
    const result = await fetch(`${server_url}/${url}`, {
        method: 'PUT',
        headers: headers,
        body: JSON.stringify(put)
    })
    return result.json();
}

export const patchData = async(url: string, patch: object) => {
    const token = localStorage.getItem('userToken') || undefined;
    const headers = {
        'Content-Type': 'application/json',
        ...(token && { 'Authorization': `Bearer ${token}` })
    };
    const result = await fetch(`${server_url}/${url}`, {
        method: 'PATCH',
        headers: headers,
        body: JSON.stringify(patch)
    })
    return result.json();
}

export const deleteData = async(url: string) => {
    const token = localStorage.getItem('userToken') || undefined;
    const headers = {
        'Content-Type': 'application/json',
        ...(token && { 'Authorization': `Bearer ${token}` })
    };
    const result = await fetch(`${server_url}/${url}`, {
        method: 'DELETE',
        headers: headers
    })
    return result.json();
}