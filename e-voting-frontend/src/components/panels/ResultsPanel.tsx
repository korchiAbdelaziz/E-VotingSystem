import React, { useState, useEffect } from 'react';
import { resultService } from '../../services/api';
import './Panel.css';

interface Result {
  candidateId: number;
  totalVotes: number;
}

interface Statistics {
  totalVotes: number;
  totalElectors: number;
  participationRate: number;
}

interface ResultsPanelProps {
  onUpdate: () => void;
}

const ResultsPanel: React.FC<ResultsPanelProps> = ({ onUpdate }) => {
  const [results, setResults] = useState<Result[]>([]);
  const [statistics, setStatistics] = useState<Statistics | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    try {
      setLoading(true);
      const [resultsRes, statsRes] = await Promise.all([
        resultService.getResults(),
        resultService.getStatistics().catch(() => null),
      ]);
      setResults(resultsRes.data || []);
      setStatistics(statsRes?.data || null);
      setError('');
    } catch (err: any) {
      setError('Erreur lors du chargement: ' + (err.message || 'Erreur inconnue'));
    } finally {
      setLoading(false);
    }
  };

  const handlePublish = async () => {
    try {
      await resultService.publishResults();
      loadData();
      onUpdate();
      alert('Résultats publiés avec succès !');
    } catch (err: any) {
      setError('Erreur lors de la publication: ' + (err.message || 'Erreur inconnue'));
    }
  };

  const totalVotes = results.reduce((sum, r) => sum + (r.totalVotes || 0), 0);
  const maxVotes = Math.max(...results.map(r => r.totalVotes || 0), 1);

  if (loading) {
    return <div className="panel-loading">Chargement...</div>;
  }

  return (
    <div className="panel">
      <div className="panel-header">
        <h2>Résultats du Vote</h2>
        <button onClick={handlePublish} className="publish-button">
          📢 Publier les résultats
        </button>
      </div>

      {error && <div className="panel-error">{error}</div>}

      {statistics && (
        <div className="statistics-cards">
          <div className="stat-card-mini">
            <div className="stat-mini-value">{statistics.totalVotes || totalVotes}</div>
            <div className="stat-mini-label">Total Votes</div>
          </div>
          <div className="stat-card-mini">
            <div className="stat-mini-value">{statistics.totalElectors || 0}</div>
            <div className="stat-mini-label">Total Électeurs</div>
          </div>
          <div className="stat-card-mini">
            <div className="stat-mini-value">
              {statistics.participationRate ? `${statistics.participationRate.toFixed(1)}%` : 'N/A'}
            </div>
            <div className="stat-mini-label">Taux de participation</div>
          </div>
        </div>
      )}

      <div className="results-container">
        {results.length === 0 ? (
          <div className="empty-state">Aucun résultat disponible</div>
        ) : (
          results.map((result) => {
            const percentage = totalVotes > 0 ? ((result.totalVotes || 0) / totalVotes * 100).toFixed(1) : 0;
            const barWidth = totalVotes > 0 ? ((result.totalVotes || 0) / maxVotes * 100) : 0;
            
            return (
              <div key={result.candidateId} className="result-card">
                <div className="result-header">
                  <h3>Candidat {result.candidateId}</h3>
                  <div className="result-votes">
                    <span className="votes-count">{result.totalVotes || 0}</span>
                    <span className="votes-label">votes</span>
                  </div>
                </div>
                <div className="result-bar-container">
                  <div 
                    className="result-bar" 
                    style={{ width: `${barWidth}%` }}
                  >
                    <span className="result-percentage">{percentage}%</span>
                  </div>
                </div>
              </div>
            );
          })
        )}
      </div>
    </div>
  );
};

export default ResultsPanel;

