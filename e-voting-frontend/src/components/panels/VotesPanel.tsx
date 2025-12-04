import React, { useState, useEffect } from 'react';
import { voteService, voterService } from '../../services/api';
import './Panel.css';

interface Vote {
  idVote: number;
  dateHeure: string;
  electorId: number;
  candidateId: number;
  electorName?: string;
}

interface VotesPanelProps {
  onUpdate: () => void;
}

const VotesPanel: React.FC<VotesPanelProps> = ({ onUpdate }) => {
  const [votes, setVotes] = useState<Vote[]>([]);
  const [electors, setElectors] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [showForm, setShowForm] = useState(false);
  const [formData, setFormData] = useState({
    electorId: '',
    candidateId: '',
  });

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    try {
      setLoading(true);
      const [votesRes, electorsRes] = await Promise.all([
        voteService.getAllVotes(),
        voterService.getAllElectors(),
      ]);
      setVotes(votesRes.data);
      setElectors(electorsRes.data);
      setError('');
    } catch (err: any) {
      setError('Erreur lors du chargement: ' + (err.message || 'Erreur inconnue'));
    } finally {
      setLoading(false);
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await voteService.submitVote({
        electorId: parseInt(formData.electorId),
        candidateId: parseInt(formData.candidateId),
      });
      setShowForm(false);
      setFormData({ electorId: '', candidateId: '' });
      loadData();
      onUpdate();
    } catch (err: any) {
      setError('Erreur: ' + (err.response?.data || err.message));
    }
  };

  const getElectorName = (electorId: number) => {
    const elector = electors.find(e => e.idElector === electorId);
    return elector ? `${elector.prenom} ${elector.nom}` : `ID: ${electorId}`;
  };

  if (loading) {
    return <div className="panel-loading">Chargement...</div>;
  }

  return (
    <div className="panel">
      <div className="panel-header">
        <h2>Gestion des Votes</h2>
        <button onClick={() => setShowForm(!showForm)} className="add-button">
          {showForm ? 'Annuler' : '+ Soumettre un vote'}
        </button>
      </div>

      {showForm && (
        <form onSubmit={handleSubmit} className="panel-form">
          <div className="form-row">
            <div className="form-group">
              <label>Électeur</label>
              <select
                value={formData.electorId}
                onChange={(e) => setFormData({ ...formData, electorId: e.target.value })}
                required
              >
                <option value="">Sélectionner un électeur</option>
                {electors.filter(e => !e.aVote).map((elector) => (
                  <option key={elector.idElector} value={elector.idElector}>
                    {elector.prenom} {elector.nom} (ID: {elector.idElector})
                  </option>
                ))}
              </select>
            </div>
            <div className="form-group">
              <label>Candidat ID</label>
              <input
                type="number"
                value={formData.candidateId}
                onChange={(e) => setFormData({ ...formData, candidateId: e.target.value })}
                min="1"
                required
              />
            </div>
          </div>
          <button type="submit" className="submit-button">Soumettre le vote</button>
        </form>
      )}

      {error && <div className="panel-error">{error}</div>}

      <div className="panel-table-container">
        <table className="panel-table">
          <thead>
            <tr>
              <th>ID Vote</th>
              <th>Électeur</th>
              <th>Candidat</th>
              <th>Date/Heure</th>
            </tr>
          </thead>
          <tbody>
            {votes.length === 0 ? (
              <tr>
                <td colSpan={4} className="empty-state">Aucun vote trouvé</td>
              </tr>
            ) : (
              votes.map((vote) => (
                <tr key={vote.idVote}>
                  <td>{vote.idVote}</td>
                  <td>{getElectorName(vote.electorId)}</td>
                  <td>Candidat {vote.candidateId}</td>
                  <td>{new Date(vote.dateHeure).toLocaleString('fr-FR')}</td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default VotesPanel;

