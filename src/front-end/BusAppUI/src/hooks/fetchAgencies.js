import { useState, useEffect } from 'react';
import { fetchAgencies } from '../api';

export const useFetchAgencies = () => {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchAgencies()
      .then((agencies) => {
        setData(agencies);
        setLoading(false);
      });
  }, []);

  return { data, loading };
};